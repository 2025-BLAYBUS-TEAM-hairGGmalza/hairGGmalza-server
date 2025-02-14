package hair.hairgg.designer.service;

import hair.hairgg.designer.domain.Designer;
import org.springframework.data.domain.Page;

public interface DesignerService {
    Designer getDesignerById(Long designerId);

    Page<Designer> getDesigners(Integer page);
}
