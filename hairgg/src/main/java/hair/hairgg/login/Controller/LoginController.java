package hair.hairgg.login.Controller;

import hair.hairgg.login.Service.LoginService;
import hair.hairgg.member.Dto.Member;
import hair.hairgg.member.Repository.MemberRepository;
import hair.hairgg.security.jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;
    private final MemberRepository memberRepository;
    private final JwtUtil jwtUtil;

    @GetMapping("/login")
    public ResponseEntity<?> googleLogin(@RequestParam("code") String code) {

        String accessToken = loginService.getGoogleAccessToken(code);

        Member userInfo = loginService.getUserInfo(accessToken);
        System.out.println("userInfo: "+userInfo);
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
    public ResponseEntity<Member> signup(@AuthenticationPrincipal UserDetails userDetails, @RequestBody Member member) {
        if (userDetails == null) {
            return ResponseEntity.status(401).body(null);
        }

        String email = userDetails.getUsername();

        Optional<Member> existMember = memberRepository.findByLoginId(email);

        Member newMember;
        if (existMember.isPresent()) {
            newMember = existMember.get();
            newMember.setNickname(member.getNickname());
            newMember.setGender(member.getGender());
            newMember.setPhoneNumber(member.getPhoneNumber());
        } else {
            newMember = new Member(email, member.getName(), member.getProfileUrl(), member.getNickname(), member.getGender(), member.getPhoneNumber());
        }
        memberRepository.save(newMember);
        return ResponseEntity.ok(newMember);
    }
}