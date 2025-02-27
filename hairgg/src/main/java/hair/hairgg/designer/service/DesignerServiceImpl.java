package hair.hairgg.designer.service;

import hair.hairgg.designer.domain.Designer;
import hair.hairgg.designer.dto.SearchFilterDto;
import hair.hairgg.designer.repository.DesignerRepository;
import hair.hairgg.exception.ErrorCode;
import hair.hairgg.exception.custom.DesignerError;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DesignerServiceImpl implements DesignerService {

    private final DesignerRepository designerRepository;

    @Override
    @Transactional(readOnly = true)
    public Designer getDesignerById(Long designerId) {
        return designerRepository.findByDesignerId(designerId).orElseThrow(
                () -> new DesignerError(ErrorCode.DESIGNER_NOT_FOUND)
        );
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Designer> getDesigners(Integer page) {
        return designerRepository.findAll(PageRequest.of(page, 30, Sort.by("id").ascending()));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Designer> getDesignersWithFilter(Integer page, SearchFilterDto filter) {
        Pageable pageable = PageRequest.of(page, 30);
        return designerRepository.searchWithFilter(pageable, filter);
    }
}
