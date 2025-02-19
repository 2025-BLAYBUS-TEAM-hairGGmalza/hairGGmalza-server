package hair.hairgg.pay;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

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
		return baseUrl + "/test/"+reservationId;
	}

	public String getCancelUrl(Long reservationId) {
		return baseUrl + "/test/fail"+reservationId;
	}

	public String getFailUrl(Long reservationId) {
		return baseUrl + "/test/fail"+reservationId;
	}
}