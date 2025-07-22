package com.halggeol.backend.products.service;

import org.springframework.transaction.annotation.Transactional;

public interface ProductDetailService {


    @Transactional(readOnly = true)
    Object getProductDetailById(String productId, String userId);
}
