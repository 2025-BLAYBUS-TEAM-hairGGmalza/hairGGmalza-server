package hair.hairgg.reservation;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import hair.hairgg.apiPayLoad.ApiResponse;
import hair.hairgg.reservation.domain.Reservation;
import hair.hairgg.reservation.service.pay.PayInfo;
import hair.hairgg.reservation.service.reservation.ReservationServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/reservation")
@RequiredArgsConstructor
public class ReservationController {

	private final ReservationServiceImpl reservationService;

	@PostMapping
	public ApiResponse<PayInfo.PayReadyInfo> createReservation(
		@Valid @RequestBody ReservationReqDto.ReservationRequest request) {
		PayInfo.PayReadyInfo payInfo = reservationService.createReservation(request);
		return ApiResponse.success("결제 요청 URL 발급 성공", payInfo);
	}

	@GetMapping("/{reservationId}/pay/completed")
	public ApiResponse<ReservationReqDto.ReservationInfo> payCompleted(
		@PathVariable Long reservationId,
		@RequestParam("pg_token") String pgToken) {
		Reservation payedReservation = reservationService.payApprove(reservationId, pgToken);
		return ApiResponse.success("결제 완료", ReservationConverter.toReservationInfo(payedReservation));
	}

	@GetMapping({"/{reservationId}/pay/canceled", "/{reservationId}/pay/failed"})
	public ApiResponse<ReservationReqDto.ReservationInfo> payCanceled(
		@PathVariable Long reservationId) {
		Reservation canceledReservation = reservationService.payCancel(reservationId);
		return ApiResponse.success("결제 취소", ReservationConverter.toReservationInfo(canceledReservation));
	}


	@GetMapping
	public ApiResponse<List<ReservationReqDto.ReservationDetailInfo>> getReservationByMemberId(
		@RequestParam Long memberId) {//TODO: 추후 토큰에서 가져오기
		List<Reservation> reservations = reservationService.getReservationByMemberId(memberId);
		return ApiResponse.success("예약 조회 성공", ReservationConverter.toReservationDetailInfoList(reservations));
	}

	@GetMapping("/designer/{designerId}/valid-time")
	public ApiResponse<List<LocalTime>> getValidTimes(@PathVariable Long designerId,
		@RequestParam LocalDate reservationDate) {
		List<LocalTime> validTimes = reservationService.getValidTimes(designerId, reservationDate);
		return ApiResponse.success("예약 가능 날짜 조회 성공", validTimes);
	}

}
