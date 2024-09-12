package com.numan947.toolrent.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record AuthenticationRequestDTO(
        @NotBlank(message = "Email is required")
        @NotEmpty(message = "Email is required")
        @Email(message = "Email is invalid")
        String email,

        @NotBlank(message = "Password is required")
        @NotEmpty(message = "Password is required")
        @Size(min = 6, message = "Password must be at least 6 characters long") // TODO: How to make this configurable?
        String password
) {
}
