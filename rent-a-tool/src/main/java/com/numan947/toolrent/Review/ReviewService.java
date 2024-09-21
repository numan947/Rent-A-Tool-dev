package com.numan947.toolrent.Review;

import com.numan947.toolrent.Review.dto.ReviewResponseDTO;
import com.numan947.toolrent.Review.dto.ToolReviewRequest;
import com.numan947.toolrent.common.dto.PageResponseDTO;
import com.numan947.toolrent.exception.OperationNotPermittedException;
import com.numan947.toolrent.tool.Tool;
import com.numan947.toolrent.tool.ToolRepository;
import com.numan947.toolrent.user.User;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final ReviewMapper reviewMapper;
    private final ToolRepository toolRepository;


    public Long saveReview(ToolReviewRequest request, Authentication connectedUser) {
        Tool tool = toolRepository.findById(request.toolId())
                .orElseThrow(() -> new EntityNotFoundException("Tool with id " + request.toolId() + " not found"));
//        User user = (User) connectedUser.getPrincipal();
        if (!tool.isShareable() || tool.isArchived()) {
            throw new OperationNotPermittedException("You cannot review this tool");
        }
        if (Objects.equals(tool.getCreatedBy(), connectedUser.getName())) {
            throw new OperationNotPermittedException("You cannot review your own tool");
        }
        ToolReview review = reviewMapper.toToolReview(request);
        review.setTool(tool);
        return reviewRepository.save(review).getId();
    }

    public PageResponseDTO<ReviewResponseDTO> findReviewsByTool(Long toolId, int page, int size, Authentication connectedUser) {
        Pageable pageable = PageRequest.of(page, size);
//        User user = (User) connectedUser.getPrincipal();
        Page<ToolReview>reviews = reviewRepository.findAllByToolId(toolId, pageable);
        List<ReviewResponseDTO> reviewResponseDTOS = reviews.map(review -> reviewMapper.ReviewResponseDTO(review, connectedUser.getName())).toList();
        return new PageResponseDTO<>(
                reviewResponseDTOS,
                reviews.getNumber(),
                reviews.getSize(),
                reviews.getTotalElements(),
                reviews.getTotalPages(),
                reviews.isFirst(),
                reviews.isLast()
        );
    }
}
