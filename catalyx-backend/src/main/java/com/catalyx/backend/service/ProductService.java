package com.catalyx.backend.service;

import com.catalyx.backend.dto.ProductRequest;
import com.catalyx.backend.dto.ProductResponse;
import com.catalyx.backend.entity.Business;
import com.catalyx.backend.entity.Product;
import com.catalyx.backend.entity.ProductImage;
import com.catalyx.backend.repository.BusinessRepository;
import com.catalyx.backend.repository.ProductRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final BusinessRepository businessRepository;
    private final FileStorageService fileStorageService;

    @Transactional
    public ProductResponse createProduct(Long userId, ProductRequest request) {
        Business business = resolveBusiness(userId);

        Product product = Product.builder()
                .business(business)
                .name(request.name())
                .sku(request.sku())
                .price(request.price())
                .description(request.description())
                .build();

        product = productRepository.save(product);

        if (!CollectionUtils.isEmpty(request.images())) {
            for (MultipartFile file : request.images()) {
                if (file == null || file.isEmpty()) {
                    continue;
                }
                String storedPath = fileStorageService.storeProductImage(product.getId(), file);
                ProductImage image = ProductImage.builder()
                        .product(product)
                        .imageUrl(storedPath)
                        .build();
                product.getImages().add(image);
            }
            product = productRepository.save(product);
        }

        return ProductResponse.from(product);
    }

    @Transactional(readOnly = true)
    public List<ProductResponse> getProducts(Long userId) {
        Business business = resolveBusiness(userId);

        return productRepository.findByBusinessIdWithImages(business.getId()).stream()
                .map(ProductResponse::from)
                .toList();
    }

    private Business resolveBusiness(Long userId) {
        return businessRepository.findByUserId(userId).stream()
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No business found for this user"));
    }
}
