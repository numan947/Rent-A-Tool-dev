package com.numan947.toolrent.tool.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record ToolRequestDTO(
        Long id, // if id is null, it means it is a new tool, otherwise it is an update

        @NotNull(message = "100")
        @NotEmpty(message = "100")
        @NotBlank(message = "100")
        String name,

        @NotNull(message = "100")
        @NotEmpty(message = "100")
        @NotBlank(message = "100")
        String description,

        @NotNull(message = "100")
        @NotEmpty(message = "100")
        @NotBlank(message = "100")
        String manufacturer,

        boolean shareable
) {
}
