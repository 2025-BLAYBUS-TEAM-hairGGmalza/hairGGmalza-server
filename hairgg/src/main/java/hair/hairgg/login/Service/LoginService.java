package hair.hairgg.login.Service;

import hair.hairgg.memberSecond.Dto.Member;
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

        Map<String, String> params = new HashMap<>();
        params.put("code", decodedCode);
        params.put("client_id", clientId);
        params.put("client_secret", clientSecret);
        params.put("redirect_uri", redirectUri);
        params.put("grant_type", "authorization_code");

        ResponseEntity<Map> response = restTemplate.postForEntity(tokenUrl, params, Map.class);

        return response.getBody() != null ? response.getBody().get("access_token").toString() : null;
    }

    public Member getUserInfo(String accessToken) {
        if (accessToken == null) {
            throw new IllegalArgumentException("Access Token이 유효하지 않습니다.");
        }

        String userInfoUrl = "https://www.googleapis.com/oauth2/v2/userinfo";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<Map> response = restTemplate.exchange(userInfoUrl, HttpMethod.GET, entity, Map.class);

        Map<String, Object> userInfo = response.getBody();
        System.out.println("userInfo: " + userInfo);

        if (userInfo == null || !userInfo.containsKey("email") || !userInfo.containsKey("name") || !userInfo.containsKey("picture")) {
            throw new IllegalStateException("사용자 정보를 가져오는 데 실패했습니다.");
        }

        return new Member(userInfo.get("id").toString(), userInfo.get("email").toString(), userInfo.get("name").toString(), userInfo.get("picture").toString(), null, null, null);
    }
}