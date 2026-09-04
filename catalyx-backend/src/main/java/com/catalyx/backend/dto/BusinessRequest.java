package com.catalyx.backend.dto;

import jakarta.validation.constraints.NotBlank;

public record BusinessRequest(

        @NotBlank(message = "Name is required")
        String name,

        String category,

        String logoUrl,

        String brandColors
) {
}
