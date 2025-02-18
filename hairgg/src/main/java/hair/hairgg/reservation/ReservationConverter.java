package hair.hairgg.reservation;

import java.util.List;

import hair.hairgg.designer.converter.DesignerConverter;
import hair.hairgg.designer.domain.Designer;
import hair.hairgg.reservation.domain.Reservation;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ReservationConverter {
	public static Reservation toEntity(ReservationReqDto.ReservationRequest request, int price, Member member,
		Designer designer) {
		return Reservation.builder()
			.member(member)
			.designer(designer)
			.meetingType(request.meetingType())
			.price(price)//TODO:디자이너의 가격을 가져와야함
			.reservationDate(request.reservationDate())
			.build();
	}

	public static ReservationReqDto.ReservationInfo toReservationInfo(Reservation newReservation) {
		return new ReservationReqDto.ReservationInfo(
			newReservation.getId(),
			newReservation.getMember().getId(),
			newReservation.getDesigner().getId(),
			newReservation.getReservationState(),
			newReservation.getMeetingType(),
			newReservation.getPrice(),
			newReservation.getReservationDate()
		);
	}

	public static List<ReservationReqDto.ReservationDetailInfo> toReservationDetailInfoList(
		List<Reservation> reservations) {
		return reservations.stream()
			.map(reservation -> {
				log.info("designer name : {}", reservation.getDesigner().getName());
				return new ReservationReqDto.ReservationDetailInfo(
					reservation.getId(),
					DesignerConverter.toDesignerInfobrief(reservation.getDesigner()),
					reservation.getReservationState(),
					reservation.getMeetingType(),
					reservation.getPrice(),
					reservation.getReservationDate()
				);
			})
			.toList();
	}
}
