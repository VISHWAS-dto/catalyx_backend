package com.catalyx.backend.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

public record ProductRequest(

        @NotBlank(message = "Name is required")
        String name,

        String sku,

        @DecimalMin(value = "0.0", inclusive = true, message = "Price must not be negative")
        BigDecimal price,

        String description,

        List<MultipartFile> images
) {
}
