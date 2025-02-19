package hair.hairgg.favorite.service;

import hair.hairgg.designer.domain.Designer;
import org.springframework.data.domain.Page;

public interface FavoriteService {
    Long checkFavoriteDesigner(Long memberId, Long designerId);

    Page<Designer> getFavoriteDesigners(Long memberId, Integer page);
}
