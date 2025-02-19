package hair.hairgg.review.converter;

import hair.hairgg.review.domain.Review;
import hair.hairgg.review.domain.ReviewImage;
import hair.hairgg.review.dto.ReviewDto.ReviewInfo;
import hair.hairgg.review.dto.ReviewDto.ReviewInfos;
import org.springframework.data.domain.Page;

import java.util.List;

public class ReviewConverter {

    public static ReviewInfo toReviewInfo(Review review) {
        List<String> imageUrls = review.getReviewImages().stream().map(ReviewImage::getImageUrl).toList();

        return ReviewInfo.builder()
                .reviewId(review.getId())
                .reservationId(review.getReservation().getId())
                .review(review.getReview())
                .score(review.getScore())
                .createdDate(review.getCreatedDate())
                .imageUrls(imageUrls)
                .build();
    }

    public static ReviewInfos toReviewInfoList(Page<Review> reviews) {
        double averageScore = reviews.stream()
                .mapToInt(Review::getScore)
                .average()
                .orElse(0.0);

        List<ReviewInfo> reviewInfos = reviews.stream()
                .map(ReviewConverter::toReviewInfo)
                .toList();

        return ReviewInfos.builder()
                .reviewInfos(reviewInfos)
                .averageScore(averageScore)
                .listSize(reviewInfos.size())
                .totalPage(reviews.getTotalPages())
                .totalElements(reviews.getTotalElements())
                .isFirst(reviews.isFirst())
                .isLast(reviews.isLast())
                .build();
    }
}
