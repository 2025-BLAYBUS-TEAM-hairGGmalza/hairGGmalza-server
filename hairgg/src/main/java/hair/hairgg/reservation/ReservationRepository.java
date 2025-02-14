package hair.hairgg.reservation;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
	List<Reservation> findByDesigner_IdAndReservationDateAndReservationStateIn(Long designerId, LocalDateTime reservationDate, List<ReservationState> reservationStates);
}
