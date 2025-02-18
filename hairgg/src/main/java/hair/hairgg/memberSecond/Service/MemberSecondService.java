package hair.hairgg.memberSecond.Service;

import hair.hairgg.memberSecond.Dto.MemberSecond;
import hair.hairgg.memberSecond.Repository.MemberSecondRepository;
import hair.hairgg.security.jwt.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberSecondService {

    private final MemberSecondRepository memberSecondRepository;
    private final JwtUtil jwtUtil;

    @Autowired
    public MemberSecondService(MemberSecondRepository memberSecondRepository, JwtUtil jwtUtil) {
        this.memberSecondRepository = memberSecondRepository;
        this.jwtUtil = jwtUtil;
    }

    public MemberSecond findByLoginId(String email) {
        return memberSecondRepository.findByLoginId(email).orElse(null);
    }

    public MemberSecond findById(Long id) {
        return memberSecondRepository.findById(id).orElse(null);
    }

    public MemberSecond getMemberFromJwt(String token) {
        String email = jwtUtil.getEmailFromToken(token);
        return findByLoginId(email);
    }
}