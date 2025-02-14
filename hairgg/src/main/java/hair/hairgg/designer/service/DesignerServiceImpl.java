package hair.hairgg.designer.service;

import hair.hairgg.designer.domain.Designer;
import hair.hairgg.designer.repository.DesignerMajorRepository;
import hair.hairgg.designer.repository.DesignerRepository;
import hair.hairgg.exception.ErrorCode;
import hair.hairgg.exception.custom.DesignerError;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DesignerServiceImpl implements DesignerService {

    private final DesignerRepository designerRepository;
    private final DesignerMajorRepository designerMajorRepository;

    @Override
    @Transactional(readOnly = true)
    public Designer getDesignerById(Long designerId) {
        return designerRepository.findById(designerId).orElseThrow(
                () -> new DesignerError(ErrorCode.DESIGNER_NOT_FOUND)
        );
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Designer> getDesigners(Integer page) {
        return designerRepository.findAll(PageRequest.of(page, 10, Sort.by("id").ascending()));
    }
}
