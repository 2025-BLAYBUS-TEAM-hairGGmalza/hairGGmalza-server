package hair.hairgg.memberSecond.Service;

import hair.hairgg.exception.ErrorCode;
import hair.hairgg.exception.GeneralException;
import hair.hairgg.memberSecond.Dto.Member;
import hair.hairgg.memberSecond.Repository.MemberRepository;
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

    public Member findById(Long id) {
        return memberRepository.findById(id).orElse(null);
    }

    public Member findByLoginId(String email) {
        return memberRepository.findByLoginId(email)
                .orElseThrow(() -> new GeneralException(ErrorCode.MEMBER_NOT_FOUND));  // 예외 처리
    }

    // JWT 토큰에서 이메일로 멤버 정보 가져오기
    public Member getMemberFromJwt(String token) {
        String email = jwtUtil.getEmailFromToken(token);
        return findByLoginId(email);
    }
}