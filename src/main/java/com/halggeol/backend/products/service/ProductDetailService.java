package com.halggeol.backend.products.service;

import com.halggeol.backend.domain.CustomUser;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

public interface ProductDetailService {

    Object getProductDetailById(String productId, @AuthenticationPrincipal CustomUser user);

    void incrementProductViewCountAsync(String productId);

    // 추천 상품 productX_status 확인
    String checkRecommendProductStatus(@AuthenticationPrincipal CustomUser user, String productId);

    void updateProductStatus(@AuthenticationPrincipal CustomUser user, String productId, String productStatus);

    public void saveCacheRegretRankingProducts();

//    void cacheUserRecommendedProducts(String userId,
//        List<RecommendResponseDTO> recommendedProducts);
}
