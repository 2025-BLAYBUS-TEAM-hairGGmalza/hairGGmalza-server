package hair.hairgg.exception;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import hair.hairgg.apiPayLoad.ApiResponse;
import hair.hairgg.exception.custom.ReservationError;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.ValidationException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class GeneralExceptionHandler {

	@ExceptionHandler(ReservationError.class)
	public ApiResponse<Object> handleReservationError(ReservationError e) {
		log.error("Reservation Error: {}", e.getMessage());
		return ApiResponse.error(e.getErrorCode());
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ApiResponse<List<Map<String,String>>> handleValidationException(MethodArgumentNotValidException e) {
		log.error("Invalid DTO Value: {}", e.getMessage());
		List<Map<String, String>> fieldErrors = e.getBindingResult().getFieldErrors().stream()
			.map(fieldError -> Map.of(
				"field", fieldError.getField(),
				"message", Objects.requireNonNull(fieldError.getDefaultMessage())
			))
			.toList();
		return ApiResponse.error("Validation failed", ErrorCode.INVALID_INPUT_VALUE, fieldErrors);
		// ConstraintViolationException 처리
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
