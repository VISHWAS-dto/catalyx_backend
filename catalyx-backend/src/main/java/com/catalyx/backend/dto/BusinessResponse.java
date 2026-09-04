package com.catalyx.backend.dto;

import com.catalyx.backend.entity.Business;
import java.time.LocalDateTime;

public record BusinessResponse(
        Long id,
        Long userId,
        String name,
        String category,
        String logoUrl,
        String brandColors,
        LocalDateTime createdAt
) {
    public static BusinessResponse from(Business business) {
        return new BusinessResponse(
                business.getId(),
                business.getUser().getId(),
                business.getName(),
                business.getCategory(),
                business.getLogoUrl(),
                business.getBrandColors(),
                business.getCreatedAt());
    }
}
