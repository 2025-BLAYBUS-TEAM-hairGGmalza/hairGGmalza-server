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

    @GetMapping("/login")
    public ResponseEntity<?> googleLogin(@RequestParam("code") String code, @RequestHeader("User-Agent") String userAgent) {

        System.out.println("code1: "+code);
        System.out.println("User-Agent: " + userAgent);
        String accessToken = loginService.getGoogleAccessToken(code);
        System.out.println("accessToken: "+accessToken);

        Member userInfo = loginService.getUserInfo(accessToken);
        System.out.println("userInfo: "+userInfo);
        String email = userInfo.getLoginId();
        String name = userInfo.getName();
        String profileUrl = userInfo.getProfileUrl();

        Optional<Member> existingMember = memberRepository.findByLoginId(email);

        if (existingMember.isPresent()) {

            String token = jwtUtil.generateToken(email);
            System.out.println("token: "+token);

            return ResponseEntity.ok().body(Map.of("token", token, "redirect", "/home"));
        } else {
            String redirectUrl = String.format("/signup?email=%s&name=%s&profileUrl=%s",
                    email,
                    name,
                    profileUrl
            );
            return ResponseEntity.status(302).header("Location", redirectUrl).build();
        }
    }

    @GetMapping("/signup")
    public ResponseEntity<?> Signup(@RequestParam("code") String code, @RequestBody Member member) {

        String accessToken = loginService.getGoogleAccessToken(code);
        System.out.println("accessToken: "+accessToken);

        Member newMember = new Member();

        newMember.setLoginId(member.getLoginId());
        newMember.setName(member.getName());
        newMember.setNickname(member.getNickname());
        newMember.setPhoneNumber(member.getPhoneNumber());
        newMember.setGender(member.getGender());
        newMember.setProfileUrl(member.getProfileUrl());

        memberRepository.save(newMember);

        String token = jwtUtil.generateToken(member.getLoginId());
        return ResponseEntity.ok().body(Map.of("token", token, "redirect", "/home"));
    }
}