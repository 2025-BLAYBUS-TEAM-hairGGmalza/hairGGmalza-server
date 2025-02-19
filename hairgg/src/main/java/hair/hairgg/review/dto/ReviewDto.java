package hair.hairgg.review.dto;

import lombok.Builder;

import java.time.LocalDate;
import java.util.List;

public class ReviewDto {

    @Builder
    public record ReviewInfo(
            Long reviewId,
            Long reservationId,
            String review,
            int score,
            LocalDate createdDate,
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
