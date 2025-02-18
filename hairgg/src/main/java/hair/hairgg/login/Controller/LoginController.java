package hair.hairgg.login.Controller;

import com.fasterxml.jackson.databind.JsonNode;
import hair.hairgg.login.Service.LoginService;
import hair.hairgg.member.Member;
import hair.hairgg.member.MemberRepository;
import hair.hairgg.security.jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

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

        System.out.println(code);

        if (code == null || code.isEmpty()) {
            System.out.println("Invalid Google Auth Code");
            return null;
        }

        String accessToken = loginService.getGoogleAccessToken(code);

        Member userInfo = loginService.getUserInfo(accessToken);
        String email = userInfo.getLoginId();

        Optional<Member> existingMember = memberRepository.findByLoginId(email);

        String token = jwtUtil.generateToken(email);
        if (existingMember.isPresent()) {
            return ResponseEntity.ok().body(Map.of("token", token, "isFirstLogin", "false"));
        } else {
            memberRepository.save(userInfo);
            return ResponseEntity.ok().body(Map.of("token", token, "isFirstLogin", "true"));
        }
    }

    @PostMapping("/signup")
    public ResponseEntity<Member> signup(@AuthenticationPrincipal Member memberDto, @RequestBody Member member) {
        if (memberDto == null) {
            return ResponseEntity.status(401).body(null);
        }

        memberDto.setNickname(member.getNickname());
        memberDto.setGender(member.getGender());
        memberDto.setPhoneNumber(member.getPhoneNumber());

        memberRepository.save(memberDto);
        return ResponseEntity.ok(memberDto);
    }
}