package com.catalyx.backend.service;

import com.catalyx.backend.dto.BusinessRequest;
import com.catalyx.backend.dto.BusinessResponse;
import com.catalyx.backend.entity.Business;
import com.catalyx.backend.entity.User;
import com.catalyx.backend.repository.BusinessRepository;
import com.catalyx.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class BusinessService {

    private final BusinessRepository businessRepository;
    private final UserRepository userRepository;

    @Transactional
    public BusinessResponse createBusiness(Long userId, BusinessRequest request) {
        if (!businessRepository.findByUserId(userId).isEmpty()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "A business already exists for this user");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not found"));

        Business business = Business.builder()
                .user(user)
                .name(request.name())
                .category(request.category())
                .logoUrl(request.logoUrl())
                .brandColors(request.brandColors())
                .build();

        business = businessRepository.save(business);
        return BusinessResponse.from(business);
    }

    public BusinessResponse getBusiness(Long userId) {
        Business business = businessRepository.findByUserId(userId).stream()
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No business found for this user"));

        return BusinessResponse.from(business);
    }
}
