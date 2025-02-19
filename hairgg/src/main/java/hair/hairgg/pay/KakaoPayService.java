package hair.hairgg.pay;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import hair.hairgg.reservation.domain.Reservation;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class KakaoPayService implements PayService {

	private static final String readyUrl = "https://open-api.kakaopay.com/online/v1/payment/ready";
	private static final String approveUrl = "https://open-api.kakaopay.com/online/v1/payment/approve";

	private final KakaoPayConfig kakaoPayConfig;
	@Autowired
	public KakaoPayService(KakaoPayConfig kakaoPayConfig) {
		this.kakaoPayConfig = kakaoPayConfig;
	}

	@Override
	public PayInfo.PayReadyInfo payReady(Reservation reservation) {
		log.info("KakaoPayService payReady");

		Map<String, String> kakaoParameters = new HashMap<>();
		kakaoParameters.put("cid", kakaoPayConfig.getCid());
		kakaoParameters.put("partner_order_id", String.valueOf(reservation.getId()));
		kakaoParameters.put("partner_user_id", String.valueOf(reservation.getMember().getId()));
		kakaoParameters.put("item_name", "헤어 컨설팅 예약");
		kakaoParameters.put("quantity", "1");
		kakaoParameters.put("total_amount", String.valueOf(reservation.getPrice()));
		kakaoParameters.put("tax_free_amount", "0");
		kakaoParameters.put("approval_url", kakaoPayConfig.getApprovalUrl(reservation.getId()));
		kakaoParameters.put("cancel_url", kakaoPayConfig.getCancelUrl(reservation.getId()));
		kakaoParameters.put("fail_url", kakaoPayConfig.getFailUrl(reservation.getId()));

		HttpEntity<Map<String, String>> entity = new HttpEntity<>(kakaoParameters, this.getHeaders());

		RestTemplate template = new RestTemplate();

		ResponseEntity<PayInfo.PayReadyInfo> response = template.postForEntity(readyUrl, entity,
			PayInfo.PayReadyInfo.class);
		log.info("approval_url : {}", kakaoPayConfig.getApprovalUrl(reservation.getId()));
		log.info("response : {}", response.getBody());
		return response.getBody();
	}

	@Override
	public PayInfo.PayApproveInfo payApprove(Reservation reservation, String pg_token) {
		log.info("KakaoPayService payApprove");

		Map<String, String> kakaoParameters = new HashMap<>();
		kakaoParameters.put("cid", kakaoPayConfig.getCid());
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
		headers.set("Authorization", "SECRET_KEY " + kakaoPayConfig.getSecretKey());
		headers.set("Content-Type", "application/json");

		return headers;
	}
}
