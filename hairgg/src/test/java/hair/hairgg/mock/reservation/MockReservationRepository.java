package hair.hairgg.mock.reservation;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;

import hair.hairgg.reservation.domain.Reservation;
import hair.hairgg.reservation.ReservationRepository;
import hair.hairgg.reservation.domain.ReservationState;

public class MockReservationRepository implements ReservationRepository {
	@Override
	public List<Reservation> findByDesigner_IdAndReservationDateAndReservationStateIn(Long designerId,
		LocalDateTime reservationDate, List<ReservationState> reservationStates) {
		return List.of();
	}

	@Override
	public List<Reservation> findByMember_IdOrderByReservationDate(Long memberId) {
		return List.of();
	}

	@Override
	public List<LocalDateTime> findReservationDateByDesignerIdAndReservationDateBetween(Long designerId,
		LocalDateTime start, LocalDateTime end) {
		return List.of();
	}

	@Override
	public void flush() {

	}

	@Override
	public <S extends Reservation> S saveAndFlush(S entity) {
		return null;
	}

	@Override
	public <S extends Reservation> List<S> saveAllAndFlush(Iterable<S> entities) {
		return List.of();
	}

	@Override
	public void deleteAllInBatch(Iterable<Reservation> entities) {

	}

	@Override
	public void deleteAllByIdInBatch(Iterable<Long> longs) {

	}

	@Override
	public void deleteAllInBatch() {

	}

	@Override
	public Reservation getOne(Long aLong) {
		return null;
	}

	@Override
	public Reservation getById(Long aLong) {
		return null;
	}

	@Override
	public Reservation getReferenceById(Long aLong) {
		return null;
	}

	@Override
	public <S extends Reservation> List<S> findAll(Example<S> example) {
		return List.of();
	}

	@Override
	public <S extends Reservation> List<S> findAll(Example<S> example, Sort sort) {
		return List.of();
	}

	@Override
	public <S extends Reservation> List<S> saveAll(Iterable<S> entities) {
		return List.of();
	}

	@Override
	public List<Reservation> findAll() {
		return List.of();
	}

	@Override
	public List<Reservation> findAllById(Iterable<Long> longs) {
		return List.of();
	}

	@Override
	public <S extends Reservation> S save(S entity) {
		return null;
	}

	@Override
	public Optional<Reservation> findById(Long aLong) {
		return Optional.empty();
	}

	@Override
	public boolean existsById(Long aLong) {
		return false;
	}

	@Override
	public long count() {
		return 0;
	}

	@Override
	public void deleteById(Long aLong) {

	}

	@Override
	public void delete(Reservation entity) {

	}

	@Override
	public void deleteAllById(Iterable<? extends Long> longs) {

	}

	@Override
	public void deleteAll(Iterable<? extends Reservation> entities) {

	}

	@Override
	public void deleteAll() {

	}

	@Override
	public List<Reservation> findAll(Sort sort) {
		return List.of();
	}

	@Override
	public Page<Reservation> findAll(Pageable pageable) {
		return null;
	}

	@Override
	public <S extends Reservation> Optional<S> findOne(Example<S> example) {
		return Optional.empty();
	}

	@Override
	public <S extends Reservation> Page<S> findAll(Example<S> example, Pageable pageable) {
		return null;
	}

	@Override
	public <S extends Reservation> long count(Example<S> example) {
		return 0;
	}

	@Override
	public <S extends Reservation> boolean exists(Example<S> example) {
		return false;
	}

	@Override
	public <S extends Reservation, R> R findBy(Example<S> example,
		Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
		return null;
	}
}
