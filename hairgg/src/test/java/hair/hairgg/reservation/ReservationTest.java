package hair.hairgg.reservation;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.Test;

import hair.hairgg.designer.domain.MeetingType;
import hair.hairgg.exception.custom.ReservationError;
import hair.hairgg.mock.designer.MockDesignerService;
import hair.hairgg.mock.member.MockMemberService;
import hair.hairgg.mock.reservation.MockReservationRepository;
import hair.hairgg.reservation.domain.Reservation;
import hair.hairgg.reservation.domain.ReservationState;
import hair.hairgg.reservation.service.ReservationService;
import hair.hairgg.reservation.service.ReservationServiceImpl;

public class ReservationTest {

	private ReservationService reservationService = new ReservationServiceImpl(
		new MyMockReservationRepository(),
		new MockMemberService(),
		new MockDesignerService()
	);

	private static class MyMockReservationRepository extends MockReservationRepository {
		@Override
		public List<Reservation> findByDesigner_IdAndReservationDateAndReservationStateIn(Long designerId,
			LocalDateTime reservationDate, List<ReservationState> reservationStates) {

			if(designerId == 1L && reservationDate.equals(LocalDateTime.parse("2021-08-01T10:00"))){
				return List.of(Reservation.builder()
					.reservationDate(LocalDateTime.parse("2021-08-01T10:00"))
					.build());
			}

			return List.of();
		}

		@Override
		public <S extends Reservation> S save(S entity) {
			return entity;
		}
	}

	@Test
	void reservation_validation_test(){
		assertThatThrownBy(() -> {
			Reservation.builder().build();
		}).isInstanceOf(Exception.class);
	}

	@Test
	void 예약하기(){
		//given
		Long designerId = 1L;
		Long memberId = 1L;
		MeetingType meetingType = MeetingType.ONLINE;
		LocalDateTime reservationDate = LocalDateTime.parse("2025-08-01T10:00");

		var request = new ReservationDto.ReservationRequest(
			designerId, memberId, meetingType, reservationDate
		);

		//when
		Reservation reservation = reservationService.createReservation(request);

		//then
		assertThat(reservation).isNotNull();
	}

	@Test
	void 예약시간_중복시_에러반환(){
		// givenr
		Long designerId = 1L;
		Long memberId = 1L;
		MeetingType meetingType = MeetingType.ONLINE;
		LocalDateTime reservationDate = LocalDateTime.parse("2021-08-01T10:00");

		var request = new ReservationDto.ReservationRequest(
			designerId, memberId, meetingType, reservationDate
		);

		//when - then
		assertThatThrownBy(() -> {
			reservationService.createReservation(request);
		}).isInstanceOf(ReservationError.class);
	}
}
