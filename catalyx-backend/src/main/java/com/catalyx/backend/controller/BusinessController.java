package com.catalyx.backend.controller;

import com.catalyx.backend.dto.BusinessRequest;
import com.catalyx.backend.dto.BusinessResponse;
import com.catalyx.backend.security.AppUserPrincipal;
import com.catalyx.backend.service.BusinessService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/business")
@RequiredArgsConstructor
public class BusinessController {

    private final BusinessService businessService;

    @PostMapping
    public ResponseEntity<BusinessResponse> createBusiness(
            @AuthenticationPrincipal AppUserPrincipal principal,
            @Valid @RequestBody BusinessRequest request) {

        BusinessResponse response = businessService.createBusiness(principal.getId(), request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<BusinessResponse> getBusiness(@AuthenticationPrincipal AppUserPrincipal principal) {
        BusinessResponse response = businessService.getBusiness(principal.getId());
        return ResponseEntity.ok(response);
    }
}
