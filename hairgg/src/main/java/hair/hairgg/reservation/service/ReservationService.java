package hair.hairgg.reservation.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import hair.hairgg.reservation.ReservationReqDto;
import hair.hairgg.reservation.domain.Reservation;
import hair.hairgg.pay.PayInfo;

public interface ReservationService {
	PayInfo.PayReadyInfo createReservation(ReservationReqDto.ReservationRequest request);

	Reservation payApprove(Long reservationId, String pgToken);

	List<Reservation> getReservationByMemberId(Long memberId);

	List<LocalTime> getValidTimes(Long designerId, LocalDate reservationDate);

	Reservation payCancel(Long reservationId);

	Reservation getReservationById(Long reservationId);
}
