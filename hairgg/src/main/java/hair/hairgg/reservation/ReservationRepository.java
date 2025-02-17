package hair.hairgg.reservation;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import hair.hairgg.reservation.domain.Reservation;
import hair.hairgg.reservation.domain.ReservationState;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
	List<Reservation> findByDesigner_IdAndReservationDateAndReservationStateIn(Long designerId, LocalDateTime reservationDate, List<ReservationState> reservationStates);

	@Query("SELECT r FROM Reservation r JOIN FETCH r.designer d JOIN FETCH d.designerMajors WHERE r.member.id = :memberId ORDER BY r.reservationDate")
	List<Reservation> findByMember_IdOrderByReservationDate(@Param("memberId") Long memberId);

	@Query("SELECT r.reservationDate FROM Reservation r WHERE r.designer.id = :designerId AND r.reservationDate BETWEEN :start AND :end")
	List<LocalDateTime> findReservationDateByDesignerIdAndReservationDateBetween(@Param("designerId") Long designerId, @Param("start") LocalDateTime start, @Param("end") LocalDateTime end);
}
