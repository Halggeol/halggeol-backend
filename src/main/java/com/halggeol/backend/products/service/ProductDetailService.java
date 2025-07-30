package com.halggeol.backend.products.service;

import com.halggeol.backend.security.domain.CustomUser;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;

public interface ProductDetailService {

    Object getProductDetailById(String productId, @AuthenticationPrincipal CustomUser user);

    void incrementProductViewCountAsync(String productId);

    // 추천 상품 productX_status 확인
    String checkRecommendProductStatus(@AuthenticationPrincipal CustomUser user, String productId);

    // TODO: Insight 충돌이 무서워 일단 ProductDetail에서 구현합니다.
    void updateProductToRegret(@AuthenticationPrincipal CustomUser user, String productId);
}
