package hair.hairgg.favorite.repository;

import hair.hairgg.designer.domain.Designer;
import hair.hairgg.favorite.domain.Favorite;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FavoriteRepository extends JpaRepository<Favorite, Long> {

    @Query("SELECT DISTINCT d " +
            "FROM Favorite f " +
            "JOIN f.member m " +
            "JOIN f.designer d " +
            "LEFT JOIN FETCH d.designerMajors dm " +
            "LEFT JOIN FETCH dm.major " +
            "WHERE f.member.id = :memberId")
    List<Designer> findDesignerByMemberId(@Param("memberId") Long memberId);

    @Query("SELECT f " +
            "FROM Favorite f " +
            "WHERE f.member.id = :memberId AND f.designer.id = :designerId")
    Favorite findFavoriteByMemberIdAndDesignerId(
            @Param("memberId") Long memberId,
            @Param("designerId") Long designerId
    );

    @Query("SELECT COUNT(DISTINCT f) " +
            "FROM Favorite f " +
            "WHERE f.member.id = :memberId")
    long countByMemberId(@Param("memberId") Long memberId);
}
