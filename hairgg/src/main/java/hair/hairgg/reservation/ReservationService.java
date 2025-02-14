package hair.hairgg.reservation;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import hair.hairgg.designer.domain.Designer;
import hair.hairgg.designer.domain.DesignerRepository;
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
public class ReservationService {

	private final ReservationRepository reservationRepository;
	private final MemberService memberService;
	private final DesignerService designerService;


	public Reservation createReservation(ReservationDto.ReservationRequest request) {
		validate(request);
		//디자이너의 가격 가져옴 디자이너 서비스 로직 가져올 것
		Member member=memberService.findById(request.memberId());
		Designer designer=designerService.getDesignerById(request.designerId());
		int price=calculatePrice(request,designer);
		//TODO: 더미값. 추후 디자이너 서비스 로직으로 변경
		Reservation newReservation = ReservationConverter.toEntity(request,price,member,designer);
		return reservationRepository.save(newReservation);
	}

	private int calculatePrice(ReservationDto.ReservationRequest request, Designer designer) {
		if(request.meetingType()== MeetingType.ONLINE) {
			return designer.getOnlinePrice();
		}
		if(request.meetingType()== MeetingType.OFFLINE) {
			return designer.getOfflinePrice();
		}
		throw new ReservationError(ErrorCode.MEETING_TYPE_INVALID);
	}

	private void validate(ReservationDto.ReservationRequest request) {
		LocalDateTime reservationTime = request.reservationDate();
		Long designerId=request.designerId();
		if (!isValidTime(request.reservationDate())) {
			throw new ReservationError(ErrorCode.RESERVATION_TIME_INVALID);
		}
		if(reservationTime.isBefore(LocalDateTime.now())) {
			throw new ReservationError(ErrorCode.RESERVATION_TIME_PAST);
		}
		if (isTimeAlreadyBooked(designerId,reservationTime)) {
			throw new ReservationError(ErrorCode.RESERVATION_TIME_ALREADY_BOOKED);
		}
	}

	private boolean isValidTime(LocalDateTime reservationTime) {
		if(reservationTime.getMinute()%30!=0) {
			return false;
		}
		return true;
	}
	private boolean isTimeAlreadyBooked(Long designerId,LocalDateTime reservationTime) {
		return !reservationRepository.findByDesigner_IdAndReservationDateAndReservationState(designerId,reservationTime,ReservationState.ACCEPTED).isEmpty();
	}
}
