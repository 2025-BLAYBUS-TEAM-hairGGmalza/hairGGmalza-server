package hair.hairgg.reservation;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import hair.hairgg.designer.domain.Designer;
import hair.hairgg.designer.domain.DesignerService;
import hair.hairgg.designer.domain.MeetingType;
import hair.hairgg.exception.ErrorCode;
import hair.hairgg.exception.custom.ReservationError;
import hair.hairgg.member.Member;
import hair.hairgg.member.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReservationServiceImpl implements ReservationService {

	private final ReservationRepository reservationRepository;
	private final MemberService memberService;
	private final DesignerService designerService;

	@Transactional
	@Override
	public Reservation createReservation(ReservationDto.ReservationRequest request) {
		validate(request);
		//디자이너의 가격 가져옴 디자이너 서비스 로직 가져올 것
		Member member = memberService.findById(request.memberId());
		Designer designer = designerService.getDesignerById(request.designerId());
		int price = designer.getPriceByMeetingType(request.meetingType());
		//TODO: 더미값. 추후 디자이너 서비스 로직으로 변경
		Reservation newReservation = ReservationConverter.toEntity(request, price, member, designer);
		return reservationRepository.save(newReservation);
	}

	private int calculatePrice(ReservationDto.ReservationRequest request, Designer designer) {
		if (request.meetingType() == MeetingType.ONLINE) {
			return designer.getOnlinePrice();
		}
		if (request.meetingType() == MeetingType.OFFLINE) {
			return designer.getOfflinePrice();
		}
		throw new ReservationError(ErrorCode.MEETING_TYPE_INVALID);
	}

	private void validate(ReservationDto.ReservationRequest request) {
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
		return !reservationRepository.findByDesigner_IdAndReservationDateAndReservationStateIn(designerId,
			reservationTime,
			List.of(ReservationState.WAITING, ReservationState.ACCEPTED)).isEmpty();
	}
}
