package com.numan947.toolrent.Review;

import com.numan947.toolrent.Review.dto.ReviewResponseDTO;
import com.numan947.toolrent.Review.dto.ToolReviewRequest;
import com.numan947.toolrent.common.dto.PageResponseDTO;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reviews")
@Tag(name = "Review", description = "Review API")
public class ReviewController {
    private final ReviewService reviewService;

    @PostMapping
    public ResponseEntity<Long>saveReview(
            @RequestBody @Valid ToolReviewRequest request,
            Authentication connectedUser
    ){
        return ResponseEntity.ok(reviewService.saveReview(request, connectedUser));
    }

    @GetMapping("/tool/{tool-id}")
    public ResponseEntity<PageResponseDTO<ReviewResponseDTO>>findReviewsByTool(
            @PathVariable("tool-id") Long toolId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            Authentication connectedUser
    ){
        return ResponseEntity.ok(reviewService.findReviewsByTool(toolId, page, size, connectedUser));
    }
}
