package hair.hairgg.memberSecond.Repository;

import hair.hairgg.memberSecond.Dto.MemberSecond;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberSecondRepository extends JpaRepository<MemberSecond, Long> {
    Optional<MemberSecond> findByLoginId(String loginId);

    Optional<MemberSecond> findById(Long id);
}