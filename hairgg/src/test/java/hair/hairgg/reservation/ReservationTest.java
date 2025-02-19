package hair.hairgg.reservation;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.junit.jupiter.api.Test;

import hair.hairgg.calendar.GoogleCalendarConfig;
import hair.hairgg.calendar.service.GoogleCalendarService;
import hair.hairgg.designer.domain.MeetingType;
import hair.hairgg.exception.custom.ReservationError;
import hair.hairgg.mock.designer.MockDesignerService;
import hair.hairgg.mock.member.MockMemberService;
import hair.hairgg.mock.reservation.MockReservationRepository;
import hair.hairgg.mock.reservation.pay.MockPayService;
import hair.hairgg.reservation.domain.Reservation;
import hair.hairgg.reservation.domain.ReservationState;
import hair.hairgg.reservation.service.ReservationService;
import hair.hairgg.reservation.service.ReservationServiceImpl;

public class ReservationTest {

	private ReservationService reservationService = new ReservationServiceImpl(
		new MyMockReservationRepository(),
		new MockPayService(),
		new MockMemberService(),
		new MockDesignerService(),
		new GoogleCalendarService(new GoogleCalendarConfig())
	);

	private static class MyMockReservationRepository extends MockReservationRepository {

		@Override
		public List<Reservation> findByDesigner_IdAndReservationDateAndReservationStateIn(Long designerId,
			LocalDateTime reservationDate, List<ReservationState> reservationStates) {

			if (designerId == 1L && reservationDate.equals(LocalDateTime.parse("2021-08-01T10:00"))) {
				return List.of(Reservation.builder()
					.reservationDate(LocalDateTime.parse("2021-08-01T10:00"))
					.build());
			}

			return List.of();
		}

		@Override
		public List<Reservation> findByMember_IdOrderByReservationDate(Long memberId) {
			return super.findByMember_IdOrderByReservationDate(memberId);
		}

		@Override
		public <S extends Reservation> S save(S entity) {
			return entity;
		}


		@Override
		public List<java.time.LocalDateTime> findReservationDateByDesignerIdAndReservationStateAndReservationDateBetween(
			Long designerId, ReservationState reservationState, java.time.LocalDateTime start,
			java.time.LocalDateTime end) {
			return List.of(
				LocalDateTime.parse(LocalDateTime.now().plusDays(1).toLocalDate() + "T10:00"),
				LocalDateTime.parse(LocalDateTime.now().plusDays(1).toLocalDate() + "T11:00")
			);
		}
	}

	@Test
	void reservation_validation_test() {
		assertThatThrownBy(() -> {
			Reservation.builder().build();
		}).isInstanceOf(Exception.class);
	}

	// @Test
	// void 예약하기() {
	// 	//given
	// 	Long designerId = 1L;
	// 	Long memberId = 1L;
	// 	MeetingType meetingType = MeetingType.ONLINE;
	// 	LocalDateTime reservationDate = LocalDateTime.parse("2025-08-01T10:00");
	//
	// 	var request = new ReservationReqDto.ReservationRequest(
	// 		designerId, memberId, meetingType, reservationDate
	// 	);
	//
	// 	// //when
	// 	// Reservation reservation = reservationService.createReservation(request);
	// 	//
	// 	// //then
	// 	// assertThat(reservation).isNotNull();
	// }

	// @Test
	// void 예약시간_중복시_에러반환() {
	// 	// givenr
	// 	Long designerId = 1L;
	// 	Long memberId = 1L;
	// 	MeetingType meetingType = MeetingType.ONLINE;
	// 	LocalDateTime reservationDate = LocalDateTime.parse("2021-08-01T10:00");
	//
	// 	var request = new ReservationReqDto.ReservationRequest(
	// 		designerId, memberId, meetingType, reservationDate
	// 	);
	//
	// 	//when - then
	// 	assertThatThrownBy(() -> {
	// 		reservationService.createReservation(memberId,request);
	// 	}).isInstanceOf(ReservationError.class);
	// }

	@Test
	void 예약조회() {
		//given
		Long memberId = 1L;

		//when
		List<Reservation> reservations = reservationService.getReservationByMemberId(memberId);

		//then
		assertThat(reservations).isNotNull();
	}

	@Test
	void 예약가능시간_조회() {
		//given
		Long designerId = 1L;
		LocalDate reservationDate = LocalDate.now().plusDays(1);

		//when
		List<LocalTime> validTimes = reservationService.getValidTimes(designerId, reservationDate);

		//then
		assertThat(validTimes).isNotIn(LocalTime.of(10, 0), LocalTime.of(11, 0));
	}

	@Test
	void 예약가능시간_조회_3개월이상_조회시_에러() {
		//given
		Long designerId = 1L;
		LocalDate reservationDate = LocalDate.parse("2026-08-01");

		//when-then
		assertThatThrownBy(() -> {
			reservationService.getValidTimes(designerId, reservationDate);
		}).isInstanceOf(ReservationError.class);
	}

	@Test
	void 예약가능시간_조회_이전날짜_조회시_에러() {
		//given
		Long designerId = 1L;
		LocalDate reservationDate = LocalDate.now().minus(1, ChronoUnit.DAYS);

		//when-then
		assertThatThrownBy(() -> {
			reservationService.getValidTimes(designerId, reservationDate);
		}).isInstanceOf(ReservationError.class);
	}
}
