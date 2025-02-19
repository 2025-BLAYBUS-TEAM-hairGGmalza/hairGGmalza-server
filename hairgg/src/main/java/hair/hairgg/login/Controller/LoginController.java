package hair.hairgg.login.Controller;

import com.fasterxml.jackson.databind.JsonNode;
import hair.hairgg.login.Service.LoginService;
import hair.hairgg.member.Member;
import hair.hairgg.member.MemberRepository;
import hair.hairgg.security.jwt.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;
    private final MemberRepository memberRepository;
    private final JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<?> googleLogin(@RequestBody JsonNode jsonNode) {
        String code = jsonNode.get("code").asText();
        if (code == null || code.isEmpty()) {
            System.out.println("Invalid Google Auth Code");
            return null;
        }

        String accessToken = loginService.getGoogleAccessToken(code);

        Member userInfo = loginService.getUserInfo(accessToken);
        String email = userInfo.getLoginId();

        Map<String, Object> map = new HashMap<>();
        map.put("googleId", userInfo.getGoogleId());
        map.put("loginId", userInfo.getLoginId());
        map.put("profileUrl", userInfo.getProfileUrl());

        Optional<Member> existingMember = memberRepository.findByLoginId(email);


        if (existingMember.isPresent()) {
            String token = jwtUtil.generateToken(map, existingMember.get().getId());
            return ResponseEntity.ok().body(Map.of("token", token, "isFirstLogin", "false"));
        } else {
            String token = jwtUtil.generateToken(map, 0L);
            return ResponseEntity.ok().body(Map.of("token", token, "userInfo", userInfo, "isFirstLogin", "true"));
        }
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(HttpServletRequest request, @RequestBody Member member) {
        Long memberId = (Long) request.getAttribute("id");

        if(!memberId.equals(0L)) return null;

        String token = request.getHeader("Authorization").substring(7);
        Claims claims = jwtUtil.extractClaims(token);

        member.setLoginId(claims.get("googleId", String.class));
        member.setProfileUrl(claims.get("profileUrl", String.class));
        member.setGoogleId(claims.get("googleId", String.class));

        Member newMember = memberRepository.save(member);

        String newToken = jwtUtil.generateToken(claims, newMember.getId());

        return ResponseEntity.ok().body(Map.of("token", newToken));
    }

    @PostMapping("/checkToken")
    public ResponseEntity<String> sign(HttpServletRequest request) {
        Long memberId = (Long) request.getAttribute("id");

        if (memberId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token");
        }

        // memberId를 사용해서 로직 처리
        return ResponseEntity.ok("Success! Member ID: " + memberId);
    }
}