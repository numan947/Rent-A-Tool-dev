package com.numan947.toolrent.tool.dto;

public record ToolTransactionDTO(
        Long id,
        String name,
        String description,
        String manufacturer,
        double averageRating,
        boolean returned,
        boolean returnApproved
) {
}
