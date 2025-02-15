package hair.hairgg.reservation;

import org.springframework.transaction.annotation.Transactional;

public interface ReservationService {
	Reservation createReservation(ReservationDto.ReservationRequest request);
}
