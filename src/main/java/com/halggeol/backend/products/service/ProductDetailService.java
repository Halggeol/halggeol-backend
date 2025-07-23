package com.halggeol.backend.products.service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.transaction.annotation.Transactional;

public interface ProductDetailService {
    Object getProductDetailById(String productId, String userId);
    void incrementProductViewCountAsync(String productId);
}
