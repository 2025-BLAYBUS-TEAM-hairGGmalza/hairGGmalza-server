package hair.hairgg.review.service;

import hair.hairgg.review.domain.Review;
import org.springframework.data.domain.Page;

public interface ReviewService {

    Page<Review> getReviewsByDesignerId(Integer page, Long designerId);
}
