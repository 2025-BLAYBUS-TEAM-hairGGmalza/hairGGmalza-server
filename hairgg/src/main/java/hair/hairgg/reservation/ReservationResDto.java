package hair.hairgg.reservation;

import java.time.LocalDateTime;

import org.aspectj.lang.ProceedingJoinPoint;

import hair.hairgg.designer.domain.MeetingType;
import hair.hairgg.designer.dto.DesignerDto;
import hair.hairgg.reservation.domain.ReservationState;
import jakarta.validation.constraints.NotNull;

public class ReservationResDto {
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

	public record ForPayInfo(
		String tid,
		String next_redirect_pc_url
	) {
	}
}
