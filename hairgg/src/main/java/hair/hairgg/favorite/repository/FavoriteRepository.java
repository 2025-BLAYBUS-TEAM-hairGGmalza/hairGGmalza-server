package hair.hairgg.favorite.repository;

import hair.hairgg.designer.domain.Designer;
import hair.hairgg.favorite.domain.Favorite;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface FavoriteRepository extends JpaRepository<Favorite, Long> {

    @Query(value = "SELECT d " +
            "FROM Favorite f " +
            "LEFT JOIN f.designer d " +
            "WHERE f.member.id = :memberId",
            countQuery = "SELECT COUNT(DISTINCT f) FROM Favorite f WHERE f.member.id = :memberId")
    Page<Designer> findDesignerByMemberId(@Param("memberId") Long memberId, Pageable pageable);

    @Query("SELECT f " +
            "FROM Favorite f " +
            "WHERE f.member.id = :memberId AND f.designer.id = :designerId")
    Favorite findFavoriteByMemberIdAndDesignerId(
            @Param("memberId") Long memberId,
            @Param("designerId") Long designerId
    );
}
