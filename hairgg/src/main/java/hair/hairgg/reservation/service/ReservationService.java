package hair.hairgg.reservation.service;

import java.util.List;

import hair.hairgg.reservation.ReservationDto;
import hair.hairgg.reservation.domain.Reservation;

public interface ReservationService {
	Reservation createReservation(ReservationDto.ReservationRequest request);
	List<Reservation> getReservationByMemberId(Long memberId);
}
