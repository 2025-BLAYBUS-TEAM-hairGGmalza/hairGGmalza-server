package hair.hairgg.designer.repository;

import hair.hairgg.designer.domain.Designer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DesignerRepository extends JpaRepository<Designer, Long> {
    Page<Designer> findAll(Pageable pageable);
}
