package hair.hairgg.apiPayLoad;

import hair.hairgg.config.exception.ErrorCode;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ApiResponse<T> {
	//상태코드
	private int status;

	//에러 발생시 에러코드, 기본 null
	private String code;

	//응답 메세지
	private String message;

	//응답 데이터
	private T data;

	// 성공 응답 생성 메서드들
	public static <T> ApiResponse<T> success(String message, T data) {
		return ApiResponse.<T>builder()
			.status(200)
			.code(null)
			.message(message)
			.data(data)
			.build();
	}
	// 에러 응답 생성 메서드
	public static <T> ApiResponse<T> error(ErrorCode errorCode) {
		return ApiResponse.<T>builder()
			.status (errorCode.getStatus())
			.code(errorCode.getCode())
			.message(errorCode.getMessage())
			.data(null)    // 에러 시에는 data를 null로
			.build();
	}
	// 에러 응답 생성 메서드(커스텀 메세지 추가)
	public static <T> ApiResponse<T> error(String message,ErrorCode errorCode,T data) {
		return ApiResponse.<T>builder()
			.status(errorCode.getStatus())
			.code(errorCode.getCode())
			.message(message)
			.data(data)
			.build();
	}
}
