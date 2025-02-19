package hair.hairgg.member;

import org.springframework.stereotype.Service;

@Service
public interface MemberService {
    Member findById(Long id);
}