package hair.hairgg.review.controller;

import hair.hairgg.apiPayLoad.ApiResponse;
import hair.hairgg.review.converter.ReviewConverter;
import hair.hairgg.review.domain.Review;
import hair.hairgg.review.dto.ReviewDto.ReviewInfos;
import hair.hairgg.review.dto.ReviewRequest;
import hair.hairgg.review.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping("")
    public ApiResponse<ReviewInfos> getReviewsByDesignerId(
            @RequestParam(name = "page", defaultValue = "0") Integer page,
            @RequestBody ReviewRequest request
    ) {
        Page<Review> reviews = reviewService.getReviewsByDesignerId(page, request.getDesignerId());

        return ApiResponse.success("리뷰 목록 조회 성공", ReviewConverter.toReviewInfoList(reviews));
    }
}
