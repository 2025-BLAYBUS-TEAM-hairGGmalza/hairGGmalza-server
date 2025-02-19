package hair.hairgg.review.repository;

import hair.hairgg.review.domain.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    @Query("SELECT r " +
            "FROM Review r " +
            "LEFT JOIN FETCH r.reviewImages ri " +
            "LEFT JOIN FETCH r.member m " +
            "WHERE r.designer.id = :designerId")
    Page<Review> findByDesignerId(@Param("designerId") Long designerId, Pageable pageable);
}
