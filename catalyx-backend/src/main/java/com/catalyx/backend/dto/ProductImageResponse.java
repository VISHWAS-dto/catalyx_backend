package com.catalyx.backend.dto;

import com.catalyx.backend.entity.ProductImage;
import java.time.LocalDateTime;

public record ProductImageResponse(
        Long id,
        String imageUrl,
        LocalDateTime uploadedAt
) {
    public static ProductImageResponse from(ProductImage image) {
        return new ProductImageResponse(
                image.getId(),
                image.getImageUrl(),
                image.getUploadedAt());
    }
}
