package hair.hairgg.member;

import hair.hairgg.exception.ErrorCode;
import hair.hairgg.exception.custom.MemberError;
import hair.hairgg.security.jwt.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final JwtUtil jwtUtil;

    @Autowired
    public MemberService(MemberRepository memberRepository, JwtUtil jwtUtil) {
        this.memberRepository = memberRepository;
        this.jwtUtil = jwtUtil;
    }

    public Member findByLoginId(String email) {
        return memberRepository.findByLoginId(email).orElse(null);
    }

    public Member findById(Long id) {
        return memberRepository.findById(id).orElseThrow(()->
                new MemberError(ErrorCode.MEMBER_NOT_FOUND)
        );
    }

    public Member getMemberFromJwt(String token) {
        String email = jwtUtil.getEmailFromToken(token);
        return findByLoginId(email);
    }
}