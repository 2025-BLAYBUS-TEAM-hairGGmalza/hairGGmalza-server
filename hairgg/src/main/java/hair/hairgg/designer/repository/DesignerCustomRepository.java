package hair.hairgg.designer.repository;

import hair.hairgg.designer.domain.Designer;
import hair.hairgg.designer.dto.SearchFilterDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DesignerCustomRepository {

    Page<Designer> searchWithFilter(Pageable pageable, SearchFilterDto filter);
}
