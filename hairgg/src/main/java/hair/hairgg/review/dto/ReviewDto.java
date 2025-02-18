package hair.hairgg.review.dto;

import lombok.Builder;

import java.util.List;

public class ReviewDto {

    @Builder
    public record ReviewInfo(
            Long reviewId,
            String review,
            int score,
            List<String> imageUrls
    ) {
    }

    @Builder
    public record ReviewInfos(
            List<ReviewInfo> reviewInfos,
            double averageScore,
            Integer listSize,
            Integer totalPage,
            Long totalElements,
            Boolean isFirst,
            Boolean isLast
    ) {
    }
}
