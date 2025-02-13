package hair.hairgg.reservation;

import java.time.LocalDateTime;

import hair.hairgg.designer.domain.MeetingType;

public class ReservationDto{
	public record ReservationRequest(
		//TODO: 추후 토큰에서 가져오기
		Long memberId,
		Long designerId,
		MeetingType meetingType,
		//yyyy-MM-dd HH:mm
		LocalDateTime reservationDate
	){}

	public record ReservationInfo(
		Long id,
		Long memberId,
		Long designerId,
		ReservationState state,
		MeetingType meetingType,
		int price,
		LocalDateTime reservationDate
	){}
}
