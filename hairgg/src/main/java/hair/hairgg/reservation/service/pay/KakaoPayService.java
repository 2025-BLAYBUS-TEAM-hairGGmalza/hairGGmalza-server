package hair.hairgg.reservation.service.pay;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import hair.hairgg.reservation.ReservationResDto;
import hair.hairgg.reservation.domain.Reservation;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class KakaoPayService implements PayService {

	private static final String TEST_CID = "TC0ONETIME";
	private static final String SECRET_KEY = "DEVF0DCC87ADBAAC86BC82BC175BE564ECF4DD96";

	private static final String readyUrl = "https://open-api.kakaopay.com/online/v1/payment/ready";
	private static final String approveUrl = "https://open-api.kakaopay.com/online/v1/payment/approve";

	@Override
	public PayInfo.PayReadyInfo payReady(Reservation reservation) {
		log.info("KakaoPayService payReady");

		Map<String, String> kakaoParameters = new HashMap<>();
		kakaoParameters.put("cid", TEST_CID);
		kakaoParameters.put("partner_order_id", String.valueOf(reservation.getId()));
		kakaoParameters.put("partner_user_id", String.valueOf(reservation.getMember().getId()));
		kakaoParameters.put("item_name", "헤어 컨설팅 예약");
		kakaoParameters.put("quantity", "1");
		kakaoParameters.put("total_amount", String.valueOf(reservation.getPrice()));
		kakaoParameters.put("tax_free_amount", "0");
		kakaoParameters.put("approval_url",
			"http://localhost:8080/reservation/" + reservation.getId() + "/pay/completed");
		kakaoParameters.put("cancel_url", "http://localhost:8080/reservation/" + reservation.getId() + "/pay/cancel");
		kakaoParameters.put("fail_url", "http://localhost:8080/reservation/" + reservation.getId() + "/pay/fail");

		HttpEntity<Map<String, String>> entity = new HttpEntity<>(kakaoParameters, this.getHeaders());

		RestTemplate template = new RestTemplate();

		ResponseEntity<PayInfo.PayReadyInfo> response = template.postForEntity(readyUrl, entity,
			PayInfo.PayReadyInfo.class);
		log.info("response : {}", response.getBody());

		return response.getBody();
	}

	@Override
	public PayInfo.PayApproveInfo payApprove(Reservation reservation, String pg_token) {
		log.info("KakaoPayService payApprove");

		Map<String, String> kakaoParameters = new HashMap<>();
		kakaoParameters.put("cid", TEST_CID);
		kakaoParameters.put("tid", String.valueOf(reservation.getTid()));
		kakaoParameters.put("partner_order_id", String.valueOf(reservation.getId()));
		kakaoParameters.put("partner_user_id", String.valueOf(reservation.getMember().getId()));
		kakaoParameters.put("pg_token", pg_token);

		HttpEntity<Map<String, String>> requestEntity = new HttpEntity<>(kakaoParameters, this.getHeaders());

		RestTemplate template = new RestTemplate();

		ResponseEntity<PayInfo.PayApproveInfo> response = template.postForEntity(approveUrl, requestEntity,
			PayInfo.PayApproveInfo.class);
		log.info("response : {}", response.getBody());

		return response.getBody();
	}

	private HttpHeaders getHeaders() {
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "SECRET_KEY " + SECRET_KEY);
		headers.set("Content-Type", "application/json");

		return headers;
	}
}
