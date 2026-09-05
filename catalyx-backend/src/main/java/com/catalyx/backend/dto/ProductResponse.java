package com.catalyx.backend.dto;

import com.catalyx.backend.entity.Product;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record ProductResponse(
        Long id,
        Long businessId,
        String name,
        String sku,
        BigDecimal price,
        String description,
        LocalDateTime createdAt,
        List<ProductImageResponse> images
) {
    public static ProductResponse from(Product product) {
        List<ProductImageResponse> images = product.getImages().stream()
                .map(ProductImageResponse::from)
                .toList();

        return new ProductResponse(
                product.getId(),
                product.getBusiness().getId(),
                product.getName(),
                product.getSku(),
                product.getPrice(),
                product.getDescription(),
                product.getCreatedAt(),
                images);
    }
}
