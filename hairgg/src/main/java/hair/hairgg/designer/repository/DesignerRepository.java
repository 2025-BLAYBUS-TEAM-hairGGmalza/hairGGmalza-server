package hair.hairgg.designer.repository;

import hair.hairgg.designer.domain.Designer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DesignerRepository extends JpaRepository<Designer, Long>, DesignerCustomRepository {
    Page<Designer> findAll(Pageable pageable);

    @Query("SELECT d " +
            "FROM Designer d " +
            "LEFT JOIN FETCH d.designerMajors dm " +
            "LEFT JOIN FETCH dm.major " +
            "WHERE d.id = :designerId")
    Optional<Designer> findByDesignerId(@Param("designerId") Long designerId);
}
