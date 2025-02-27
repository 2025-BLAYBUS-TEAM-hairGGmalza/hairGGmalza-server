package hair.hairgg.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    // 서버 에러
    INVALID_INPUT_VALUE(400, "S001", "잘못된 입력값입니다."),
    INTERNAL_SERVER_ERROR(500, "S002", "서버 에러가 발생했습니다."),

    //추가 도메인별 에러

	//member
	MEMBER_NOT_FOUND(404, "M001", "회원을 찾을 수 없습니다."),

	//reservation
	RESERVATION_NOT_FOUND(404, "R001", "예약을 찾을 수 없습니다."),
	RESERVATION_TIME_INVALID(400, "R002", "예약 시간은 30분 단위로 가능합니다."),
	RESERVATION_TIME_ALREADY_BOOKED(400, "R003", "예약 시간이 중복됩니다."),
	RESERVATION_TIME_PAST(400, "R004", "예약 시간은 현재 시간보다 뒤여야 합니다."),
	MEETING_TYPE_INVALID(400, "R005", "미팅 방식이 올바르지 않습니다."),
	RESERVATION_TIME_TOO_FAR(400, "R006", "예약은 3개월 이내로 가능합니다."),
	REFUND_ACCOUNT_NUMBER_REQUIRED(400, "R007", "환불 계좌번호와 은행이 필요합니다."),
	CALENDAR_EVENT_CREATE_ERROR(500, "R008", "google측 오류로 meet url 생성에 실패했습니다."),
	INVALID_INPUT_VALUE_PAYMENT_METHOD(400, "R009", "결제 방식이 올바르지 않습니다."),

	//designer
	DESIGNER_NOT_FOUND(404, "D001", "디자이너를 찾을 수 없습니다."),
	PRICE_NOT_FOUND(404, "D002", "가격을 찾을 수 없습니다.");

    private final int status;
    private final String code;
    private final String message;
}
