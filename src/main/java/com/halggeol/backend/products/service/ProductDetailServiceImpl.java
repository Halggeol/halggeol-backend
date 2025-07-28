package com.halggeol.backend.products.service;

import static com.halggeol.backend.common.ProductPrefixHandler.handleProductByBiFunction;
import static com.halggeol.backend.common.ProductPrefixHandler.handleProductByConsumer;

import com.halggeol.backend.products.mapper.ProductDetailMapper;
import com.halggeol.backend.security.domain.CustomUser;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductDetailServiceImpl implements ProductDetailService {

    private final ProductDetailMapper productDetailMapper;

    @Override
    @Transactional
    public Object getProductDetailById(String productId, @AuthenticationPrincipal CustomUser user) {

        String userId = String.valueOf(user.getUser().getId());
        // 조회수 증가 로직 먼저 호출 --> 비동기적으로 처리
        incrementProductViewCountAsync(productId);

//        // productId의 첫 글자를 확인하여 상품 유형 확인
//        if (productId == null || productId.isEmpty()) {
//            throw new IllegalArgumentException("Product ID cannot be null or empty.");
//        }

        return handleProductByBiFunction(
            productId,
            userId,
            productDetailMapper::selectDepositDetailById,
            productDetailMapper::selectSavingsDetailById,
            productDetailMapper::selectFundDetailById,
            productDetailMapper::selectForexDetailById,
            productDetailMapper::selectPensionDetailById);
    }

    @Override
    @Async
    @Transactional
    public void incrementProductViewCountAsync(String productId) {
        handleProductByConsumer(
            productId,
            productDetailMapper::incrementDepositViewCount,
            productDetailMapper::incrementSavingsViewCount,
            productDetailMapper::incrementFundViewCount,
            productDetailMapper::incrementForexViewCount,
            productDetailMapper::incrementPensionViewCount);
    }
}
