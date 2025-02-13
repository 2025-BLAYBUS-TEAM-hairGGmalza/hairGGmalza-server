package hair.hairgg.config.exception;

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


	//reservation
	RESERVATION_NOT_FOUND(404, "R001", "예약을 찾을 수 없습니다.");

	//designer

	private final int status;
	private final String code;
	private final String message;
}
