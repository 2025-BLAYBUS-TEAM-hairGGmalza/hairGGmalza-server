package hair.hairgg.reservation;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hair.hairgg.apiPayLoad.ApiResponse;
import hair.hairgg.config.exception.ErrorCode;
import hair.hairgg.config.exception.custom.ReservationError;

@RestController
@RequestMapping("/reservation")
public class ReservationController {

	//응답으로 <>안에 DTO 넣어주기
	@GetMapping
	public ApiResponse<ReservationDto.ReservationInfo> getReservation() {

		//에러 던지려면
		// throw new ReservationError(ErrorCode.RESERVATION_NOT_FOUND);

		//기본 응답
		return ApiResponse.success("예약 조회 성공", new ReservationDto.ReservationInfo(1L));
	}
}
