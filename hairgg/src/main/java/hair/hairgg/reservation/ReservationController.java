package hair.hairgg.reservation;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hair.hairgg.apiPayLoad.ApiResponse;
import hair.hairgg.exception.ErrorCode;
import hair.hairgg.exception.custom.ReservationError;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/reservation")
@RequiredArgsConstructor
public class ReservationController {

	private final ReservationService reservationService;

	@PostMapping
	public ApiResponse<ReservationDto.ReservationInfo> createReservation(
		@Valid @RequestBody ReservationDto.ReservationRequest request) {
		Reservation newReservation = reservationService.createReservation(request);
		return ApiResponse.success("예약 생성 성공", ReservationConverter.toReservationInfo(newReservation));
	}
}
