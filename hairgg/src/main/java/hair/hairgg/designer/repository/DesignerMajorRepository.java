package hair.hairgg.designer.repository;

import hair.hairgg.designer.domain.DesignerMajor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DesignerMajorRepository extends JpaRepository<DesignerMajor, Long> {
}
