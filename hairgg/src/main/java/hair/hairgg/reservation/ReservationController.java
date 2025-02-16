package hair.hairgg.reservation;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import hair.hairgg.apiPayLoad.ApiResponse;
import hair.hairgg.reservation.domain.Reservation;
import hair.hairgg.reservation.service.ReservationServiceImpl;
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

	@GetMapping
	public ApiResponse<List<ReservationDto.ReservationDetailInfo>> getReservationByMemberId(@RequestParam Long memberId) {//TODO: 추후 토큰에서 가져오기
		List<Reservation> reservations = reservationService.getReservationByMemberId(memberId);
		return ApiResponse.success("예약 조회 성공",ReservationConverter.toReservationDetailInfoList(reservations));
	}
}
