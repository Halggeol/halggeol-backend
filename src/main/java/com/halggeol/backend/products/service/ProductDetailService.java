package com.halggeol.backend.products.service;

import com.halggeol.backend.security.domain.CustomUser;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;

public interface ProductDetailService {

    @Transactional
    Object getProductDetailById(String productId, @AuthenticationPrincipal CustomUser user);

    void incrementProductViewCountAsync(String productId);
}
