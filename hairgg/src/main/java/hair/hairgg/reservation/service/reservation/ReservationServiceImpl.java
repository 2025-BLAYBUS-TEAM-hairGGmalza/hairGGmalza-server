package hair.hairgg.reservation.service.reservation;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import hair.hairgg.designer.domain.Designer;
import hair.hairgg.designer.service.DesignerService;
import hair.hairgg.exception.ErrorCode;
import hair.hairgg.exception.custom.ReservationError;
import hair.hairgg.member.Member;
import hair.hairgg.member.MemberService;
import hair.hairgg.reservation.ReservationConverter;
import hair.hairgg.reservation.ReservationReqDto;
import hair.hairgg.reservation.ReservationRepository;
import hair.hairgg.reservation.ReservationResDto;
import hair.hairgg.reservation.domain.Reservation;
import hair.hairgg.reservation.domain.ReservationState;
import hair.hairgg.reservation.service.ValidTimeManager;
import hair.hairgg.reservation.service.pay.PayInfo;
import hair.hairgg.reservation.service.pay.PayService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReservationServiceImpl implements ReservationService {

	private final ReservationRepository reservationRepository;
	private final PayService payService;
	private final MemberService memberService;
	private final DesignerService designerService;

	@Transactional
	@Override
	public PayInfo.PayReadyInfo createReservation(ReservationReqDto.ReservationRequest request) {
		validateCreateReservation(request);
		Member member = memberService.findById(request.memberId());
		Designer designer = designerService.getDesignerById(request.designerId());
		int price = designer.getPriceByMeetingType(request.meetingType());
		Reservation newReservation = ReservationConverter.toEntity(request, price, member, designer);
		reservationRepository.save(newReservation);
		PayInfo.PayReadyInfo payInfo = payService.payReady(newReservation);
		newReservation.updateTid(payInfo.tid());
		reservationRepository.save(newReservation);
		return payInfo;
	}

	@Transactional
	@Override
	public Reservation payApprove(Long reservationId, String pgToken) {
		Reservation reservation = reservationRepository.findById(reservationId)
			.orElseThrow(() -> new ReservationError(ErrorCode.RESERVATION_NOT_FOUND));
		PayInfo.PayApproveInfo payInfo = payService.payApprove(reservation, pgToken);
		// 예약 상태 변경
		reservation.updatePaymentInfo(payInfo.approved_at());
		reservation.changeState(ReservationState.PAYMENT_COMPLETED);
		return reservationRepository.save(reservation);
	}

	@Transactional
	@Override
	public Reservation payCancel(Long reservationId) {
		Reservation reservation = reservationRepository.findById(reservationId)
			.orElseThrow(() -> new ReservationError(ErrorCode.RESERVATION_NOT_FOUND));
		if(reservation.getReservationState()==ReservationState.CANCELED){
			return reservation;
		}
		reservation.changeState(ReservationState.CANCELED);
		return reservationRepository.save(reservation);
	}


	@Transactional(readOnly = true)
	@Override
	public List<Reservation> getReservationByMemberId(Long memberId) {
		//TODO:member 있는지 확인 필요
		return reservationRepository.findByMember_IdOrderByReservationDate(memberId);
	}

	@Transactional
	@Override
	public Reservation getReservationById(Long reservationId) {
		return reservationRepository.findById(reservationId)
			.orElseThrow(() -> new ReservationError(ErrorCode.RESERVATION_NOT_FOUND));
	}

	@Transactional(readOnly = true)
	@Override
	public List<LocalTime> getValidTimes(Long designerId, LocalDate reservationDate) {
		validateValidTimes(designerId, reservationDate);
		List<LocalDateTime> reservatedTimes = reservationRepository.findReservationDateByDesignerIdAndReservationStateAndReservationDateBetween(
			designerId, ReservationState.CANCELED,reservationDate.atTime(10, 0), reservationDate.atTime(20, 0));
		return ValidTimeManager.getValidTimes(reservatedTimes);

	}


	private void validateValidTimes(Long designerId, LocalDate reservationDate) {
		if (reservationDate.isBefore(LocalDate.now())) {
			throw new ReservationError(ErrorCode.RESERVATION_TIME_PAST);
		}
		if (reservationDate.isEqual(LocalDate.now())) {
			if (LocalTime.now().isAfter(LocalTime.of(20, 0))) {
				throw new ReservationError(ErrorCode.RESERVATION_TIME_PAST);
			}
		}
		if (reservationDate.isAfter(LocalDate.now().plusDays(90))) {
			throw new ReservationError(ErrorCode.RESERVATION_TIME_TOO_FAR);
		}
	}

	private void validateCreateReservation(ReservationReqDto.ReservationRequest request) {
		LocalDateTime reservationTime = request.reservationDate();
		Long designerId = request.designerId();
		if (!isValidTime(request.reservationDate())) {
			throw new ReservationError(ErrorCode.RESERVATION_TIME_INVALID);
		}
		if (reservationTime.isBefore(LocalDateTime.now())) {
			throw new ReservationError(ErrorCode.RESERVATION_TIME_PAST);
		}
		if (isTimeAlreadyBooked(designerId, reservationTime)) {
			throw new ReservationError(ErrorCode.RESERVATION_TIME_ALREADY_BOOKED);
		}
	}

	// 예약 시간은 30분 단위 이어야 함
	private boolean isValidTime(LocalDateTime reservationTime) {
		return reservationTime.getMinute() % 30 == 0;
	}

	private boolean isTimeAlreadyBooked(Long designerId, LocalDateTime reservationTime) {
		return !reservationRepository.findByDesigner_IdAndReservationDateAndReservationState(designerId,
			reservationTime,
			ReservationState.PAYMENT_COMPLETED).isEmpty();
	}
}
