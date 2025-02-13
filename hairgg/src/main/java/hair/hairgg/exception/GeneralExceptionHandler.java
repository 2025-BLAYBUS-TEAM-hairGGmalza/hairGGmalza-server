package hair.hairgg.exception;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import hair.hairgg.apiPayLoad.ApiResponse;
import hair.hairgg.exception.custom.ReservationError;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class GeneralExceptionHandler {

	@ExceptionHandler(ReservationError.class)
	public ApiResponse<Object> handleReservationError(ReservationError e) {
		log.error("Reservation Error: {}", e.getMessage());
		return ApiResponse.error(e.getErrorCode());
	}

	@ExceptionHandler(RuntimeException.class)
	public ApiResponse<Object> handleRuntimeException(RuntimeException e) {
		log.error("Runtime Exception: {}", e.getMessage());

		// GeneralException인 경우
		if (e instanceof GeneralException) {
			GeneralException generalException = (GeneralException)e;
			return ApiResponse.error(generalException.getErrorCode());
		}

		// 그 외의 RuntimeException인 경우
		return ApiResponse.error(ErrorCode.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(Exception.class)
	public ApiResponse<Object> handleException(Exception e) {
		log.error("Internal Server Error: {}", e.getMessage());
		return ApiResponse.error(ErrorCode.INTERNAL_SERVER_ERROR);
	}
}
