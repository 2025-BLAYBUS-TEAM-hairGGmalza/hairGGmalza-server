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
            return ResponseEntity.status(401).body(null);  // Unauthorized 응답을 null로 처리
        }

        // @AuthenticationPrincipal을 통해 로그인된 사용자의 이메일을 가져옵니다
        String email = userDetails.getUsername();

        Optional<Member> existMember = memberRepository.findByLoginId(email);

        Member newMember;
        if (existMember.isPresent()) {
            // 기존 회원이 있을 경우 정보 업데이트
            newMember = existMember.get();
            newMember.setNickname(member.getNickname());
            newMember.setGender(member.getGender());
            newMember.setPhoneNumber(member.getPhoneNumber());
        } else {
            // 기존 회원이 없을 경우, 새로운 회원 생성
            newMember = new Member(email, member.getName(), member.getProfileUrl(), member.getNickname(), member.getGender(), member.getPhoneNumber());
        }
        memberRepository.save(newMember);
        return ResponseEntity.ok(newMember);  // 업데이트된 회원 정보 반환
    }
}