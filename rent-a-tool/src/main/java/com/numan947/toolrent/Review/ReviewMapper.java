package com.numan947.toolrent.Review;

import com.numan947.toolrent.Review.dto.ReviewResponseDTO;
import com.numan947.toolrent.Review.dto.ToolReviewRequest;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class ReviewMapper {
    public ToolReview toToolReview(ToolReviewRequest request) {
        return ToolReview.builder()
                .id(request.reviewId())
                .rating(request.rating())
                .title(request.title())
                .content(request.description())
                .build();
    }

    public ReviewResponseDTO ReviewResponseDTO(ToolReview review, Long userId) {
        return new ReviewResponseDTO(
                review.getId(),
                review.getTitle(),
                review.getContent(),
                review.getRating(),
                Objects.equals(review.getCreatedBy(), userId)
        );
    }
}
