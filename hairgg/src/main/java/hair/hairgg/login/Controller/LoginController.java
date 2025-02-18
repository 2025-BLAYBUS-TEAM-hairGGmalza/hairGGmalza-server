package hair.hairgg.login.Controller;

import hair.hairgg.login.Service.LoginService;
import hair.hairgg.memberSecond.Dto.MemberSecond;
import hair.hairgg.memberSecond.Repository.MemberSecondRepository;
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
    private final MemberSecondRepository memberSecondRepository;
    private final JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<?> googleLogin(@RequestParam("code") String code) {

        String accessToken = loginService.getGoogleAccessToken(code);

        MemberSecond userInfo = loginService.getUserInfo(accessToken);
        String email = userInfo.getLoginId();

        Optional<MemberSecond> existingMember = memberSecondRepository.findByLoginId(email);

        String token = jwtUtil.generateToken(email);
        if (existingMember.isPresent()) {
            return ResponseEntity.ok().body(Map.of("token", token, "isFirstLogin", "false"));
        } else {
            memberSecondRepository.save(userInfo);
            return ResponseEntity.ok().body(Map.of("token", token, "isFirstLogin", "true"));
        }
    }

    @PostMapping("/signup")
    public ResponseEntity<MemberSecond> signup(@AuthenticationPrincipal MemberSecond memberDto, @RequestBody MemberSecond member) {
        if (memberDto == null) {
            return ResponseEntity.status(401).body(null);
        }

        memberDto.setNickname(member.getNickname());
        memberDto.setGender(member.getGender());
        memberDto.setPhoneNumber(member.getPhoneNumber());

        memberSecondRepository.save(memberDto);
        return ResponseEntity.ok(memberDto);
    }
}