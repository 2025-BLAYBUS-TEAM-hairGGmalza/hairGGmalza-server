package hair.hairgg.reservation;

import org.springframework.web.bind.annotation.*;

import hair.hairgg.apiPayLoad.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/reservation")
@RequiredArgsConstructor
public class ReservationController {

	private final ReservationServiceImpl reservationService;

	@PostMapping
	public ApiResponse<ReservationDto.ReservationInfo> createReservation(
		@Valid @RequestBody ReservationDto.ReservationRequest request) {
		Reservation newReservation = reservationService.createReservation(request);
		return ApiResponse.success("예약 생성 성공", ReservationConverter.toReservationInfo(newReservation));
	}

	@GetMapping("/test1")
	public String getTest() {
		return "success";
	}
}
