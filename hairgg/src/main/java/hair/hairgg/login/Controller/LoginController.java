package hair.hairgg.login.Controller;

import hair.hairgg.login.Service.LoginService;
import hair.hairgg.member.Member;
import hair.hairgg.member.memberRepository.MemberRepository;
import hair.hairgg.security.jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<?> googleLogin(@RequestParam("code") String code, @RequestHeader("User-Agent") String userAgent) {

        String accessToken = loginService.getGoogleAccessToken(code);

        Member userInfo = loginService.getUserInfo(accessToken);

        String email = userInfo.getLoginId();
        String name = userInfo.getName();
        String profileUrl = userInfo.getProfileUrl();

        Optional<Member> existingMember = memberRepository.findByLoginId(email);

        if (existingMember.isPresent()) {

            String token = jwtUtil.generateToken(email);
            return ResponseEntity.ok().body(Map.of("token", token, "isFirstLogin", "false"));
        } else {
            String redirectUrl = String.format("/signup?email=%s&name=%s&profileUrl=%s&isFirstLogin=true",
                    email,
                    name,
                    profileUrl
            );
            return ResponseEntity.status(302).header("Location", redirectUrl).build();
        }
    }

    @PostMapping("/signup")
    public ResponseEntity<?> Signup(@RequestBody Member member) {

        memberRepository.save(member);

        String token = jwtUtil.generateToken(member.getLoginId());
        return ResponseEntity.ok().body(Map.of("token", token, "isFirstLogin", "false"));
    }
}