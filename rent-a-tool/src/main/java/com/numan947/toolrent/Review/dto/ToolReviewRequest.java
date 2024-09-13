package com.numan947.toolrent.Review.dto;

import jakarta.validation.constraints.*;

public record ToolReviewRequest(
        @PositiveOrZero(message = "200")
        @Min(value = 0, message = "201")
        @Max(value = 10, message = "202")
        Double rating,

        @NotNull(message = "203")
        @NotBlank(message = "203")
        @NotEmpty(message = "203")
        String title,

        @NotNull(message = "204")
        Long toolId,

        @NotNull(message = "205")
        @NotBlank(message = "205")
        @NotEmpty(message = "205")
        @Size(min = 10, max = 500, message = "205")
        String description,

        Long reviewId
) {
}
