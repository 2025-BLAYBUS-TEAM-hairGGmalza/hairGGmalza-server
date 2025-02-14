package hair.hairgg.reservation;

import hair.hairgg.designer.domain.Designer;
import hair.hairgg.member.Member;

public class ReservationConverter {
	public static Reservation toEntity(ReservationDto.ReservationRequest request,int price,Member member,Designer designer) {
		return Reservation.builder()
			.member(member)
			.designer(designer)
			.meetingType(request.meetingType())
			.price(price)//TODO:디자이너의 가격을 가져와야함
			.reservationDate(request.reservationDate())
			.build();
	}

	public static ReservationDto.ReservationInfo toReservationInfo(Reservation newReservation) {
		return new ReservationDto.ReservationInfo(
			newReservation.getId(),
			newReservation.getMember().getId(),
			newReservation.getDesigner().getId(),
			newReservation.getReservationState(),
			newReservation.getMeetingType(),
			newReservation.getPrice(),
			newReservation.getReservationDate()
		);
	}
}
