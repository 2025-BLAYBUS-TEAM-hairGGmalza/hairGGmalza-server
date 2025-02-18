package hair.hairgg.review.service;

import hair.hairgg.designer.domain.Designer;
import hair.hairgg.designer.service.DesignerService;
import hair.hairgg.review.domain.Review;
import hair.hairgg.review.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final DesignerService designerService;
    private final ReviewRepository reviewRepository;

    @Override
    @Transactional(readOnly = true)
    public Page<Review> getReviewsByDesignerId(Integer page, Long designerId) {
        Designer designer = designerService.getDesignerById(designerId);

        Pageable pageable = PageRequest.of(page, 30);
        return reviewRepository.findByDesignerId(designer.getId(), pageable);
    }
}
