package hair.hairgg.reservation.service.pay;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import lombok.Getter;

@Configuration
public class KakaoPayConfig {

	@Getter
	@Value("${kakao.pay.secret-key}")
	private String secretKey;

	@Getter
	@Value("${kakao.pay.cid}")
	private String cid;

	@Value("${kakao.pay.base-url}")
	private String baseUrl;

	public String getApprovalUrl(Long reservationId) {
		return baseUrl + "/reservation/" + reservationId + "/pay/completed";
	}

	public String getCancelUrl(Long reservationId) {
		return baseUrl + "/reservation/" + reservationId + "/pay/cancel";
	}

	public String getFailUrl(Long reservationId) {
		return baseUrl + "/reservation/" + reservationId + "/pay/fail";
	}
}