package com.Memora.backend.mainboard.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record TextInputRequest(
        @NotBlank(message = "Text must not be blank")
        @Size(max = 10000, message = "Text must not exceed 10000 characters")
        String text
) {
}
