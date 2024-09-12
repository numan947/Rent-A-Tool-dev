package com.numan947.toolrent.tool.dto;

public record ToolResponseDTO(
        Long id,
        String name,
        String description,
        String manufacturer,
        boolean shareable,
        boolean archived,
        byte[] photo,
        double averageRating,
        String ownerName
) {
}
