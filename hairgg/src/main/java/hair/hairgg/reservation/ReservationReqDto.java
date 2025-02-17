package hair.hairgg.reservation;

import java.time.LocalDateTime;

import hair.hairgg.designer.domain.MeetingType;
import hair.hairgg.designer.dto.DesignerDto;
import hair.hairgg.reservation.domain.ReservationState;
import jakarta.validation.constraints.NotNull;

public class ReservationReqDto {
	public record ReservationRequest(
		//TODO: 추후 토큰에서 가져오기
		@NotNull(message = "회원 아이디는 필수 입력값입니다.")
		Long memberId,
		@NotNull(message = "디자이너 아이디는 필수 입력값입니다.")
		Long designerId,
		@NotNull(message = "예약 타입은 필수 입력값입니다.")
		MeetingType meetingType,
		@NotNull(message = "예약 날짜는 필수 입력값입니다.")
		LocalDateTime reservationDate
	) {
	}

	public record ReservationInfo(
		Long id,
		Long memberId,
		Long designerId,
		ReservationState state,
		MeetingType meetingType,
		int price,
		LocalDateTime reservationDate
	) {
	}

	public record ReservationDetailInfo(
		Long id,
		DesignerDto.DesignerInfobrief designer,
		ReservationState state,
		MeetingType meetingType,
		int price,
		LocalDateTime reservationDate
	) {
	}
}
