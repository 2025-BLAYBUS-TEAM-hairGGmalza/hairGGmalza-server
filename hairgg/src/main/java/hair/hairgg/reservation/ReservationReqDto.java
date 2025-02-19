package hair.hairgg.reservation;

import java.time.LocalDateTime;

import hair.hairgg.designer.domain.MeetingType;
import hair.hairgg.designer.dto.DesignerDto;
import hair.hairgg.reservation.domain.ReservationState;
import hair.hairgg.reservation.domain.pay.PaymentMethod;
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
		@NotNull(message = "예약 시간은 필수 입력값입니다.")
		LocalDateTime reservationDate,
		String message,
		@NotNull(message = "결제 수단은 필수 입력값입니다.")
		PaymentMethod paymentMethod,
		String RefundAccountNumber
	) {
	}
}
