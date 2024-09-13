package com.numan947.toolrent.Review.dto;

public record ReviewResponseDTO(
        Long id,
        String title,
        String content,
        Double rating,
        boolean isOwner // true if the user is the owner of the review
) {
}
