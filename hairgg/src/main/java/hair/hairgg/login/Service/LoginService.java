package hair.hairgg.login.Service;

import hair.hairgg.member.Dto.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class LoginService {

    @Value("${spring.security.oauth2.client.registration.google.client-id}")
    private String clientId;

    @Value("${spring.security.oauth2.client.registration.google.client-secret}")
    private String clientSecret;

    @Value("${spring.security.oauth2.client.registration.google.redirect-uri}")
    private String redirectUri;

    private final RestTemplate restTemplate = new RestTemplate();

    public String getGoogleAccessToken(String code) {
        String decodedCode = URLDecoder.decode(code, StandardCharsets.UTF_8);

        String tokenUrl = "https://oauth2.googleapis.com/token";

        System.out.println("code2: "+ decodedCode);
        System.out.println("client-id: "+ clientId);
        System.out.println("client-secret: "+ clientSecret);
        System.out.println("redirect_uri: "+ redirectUri);

        Map<String, String> params = new HashMap<>();
        params.put("code", decodedCode);
        params.put("client_id", clientId);
        params.put("client_secret", clientSecret);
        params.put("redirect_uri", redirectUri);
        params.put("grant_type", "authorization_code");
        System.out.println("params: "+ params);

        // 정확한 제네릭 타입 지정
        ResponseEntity<Map> response = restTemplate.postForEntity(tokenUrl, params, Map.class);
        System.out.println("Response body: " + response.getBody());

        // access_token이 없으면 null 반환
        return response.getBody() != null ? response.getBody().get("access_token").toString() : null;
    }

    // 액세스 토큰으로 사용자 정보 요청
    public Member getUserInfo(String accessToken) {
        if (accessToken == null) {
            // 예외를 던져서 처리
            throw new IllegalArgumentException("Access Token이 유효하지 않습니다.");
        }

        String userInfoUrl = "https://www.googleapis.com/oauth2/v2/userinfo";

        System.out.println("accessToken1: "+ accessToken);
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);

        HttpEntity<String> entity = new HttpEntity<>(headers);
        // 정확한 제네릭 타입 지정
        ResponseEntity<Map> response = restTemplate.exchange(userInfoUrl, HttpMethod.GET, entity, Map.class);

        Map<String, Object> userInfo = response.getBody();

        if (userInfo == null || !userInfo.containsKey("email") || !userInfo.containsKey("name") || !userInfo.containsKey("picture")) {
            // 필수 정보가 없을 경우 처리
            throw new IllegalStateException("사용자 정보를 가져오는 데 실패했습니다.");
        }

        return new Member(null, userInfo.get("email").toString(), userInfo.get("name").toString(), null, null, null, userInfo.get("picture").toString());
    }
}