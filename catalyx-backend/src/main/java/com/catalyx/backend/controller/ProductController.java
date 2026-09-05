package com.catalyx.backend.controller;

import com.catalyx.backend.dto.ProductRequest;
import com.catalyx.backend.dto.ProductResponse;
import com.catalyx.backend.security.AppUserPrincipal;
import com.catalyx.backend.service.ProductService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ProductResponse> createProduct(
            @AuthenticationPrincipal AppUserPrincipal principal,
            @Valid @ModelAttribute ProductRequest request) {

        ProductResponse response = productService.createProduct(principal.getId(), request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<ProductResponse>> getProducts(@AuthenticationPrincipal AppUserPrincipal principal) {
        List<ProductResponse> products = productService.getProducts(principal.getId());
        return ResponseEntity.ok(products);
    }
}
