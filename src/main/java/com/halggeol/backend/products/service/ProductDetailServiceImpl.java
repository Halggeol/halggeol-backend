package com.halggeol.backend.products.service;

import com.halggeol.backend.products.mapper.ProductDetailMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductDetailServiceImpl implements ProductDetailService {

    private final ProductDetailMapper productDetailMapper;

    @Override
    @Transactional
    public Object getProductDetailById(String productId, String userId) {

        // 조회수 증가 로직 먼저 호출 --> 비동기적으로 처리
        incrementProductViewCountAsync(productId);

//        // productId의 첫 글자를 확인하여 상품 유형 확인
//        if (productId == null || productId.isEmpty()) {
//            throw new IllegalArgumentException("Product ID cannot be null or empty.");
//        }

        char prefix = productId.charAt(0);

        if (prefix == 'D') {
            // Deposit 상품인 경우

            return productDetailMapper.selectDepositDetailById(productId, userId);
        } else if (prefix == 'S') {
            // Savings 상품인 경우
            return productDetailMapper.selectSavingsDetailById(productId, userId);
        } else if (prefix == 'F') {
            // Fund 상품인 경우
            return productDetailMapper.selectFundDetailById(productId, userId);
        } else if (prefix == 'X') {
            // Forex 상품인 경우
            return productDetailMapper.selectForexDetailById(productId, userId);
        } else if (prefix == 'A' || prefix == 'C') {
            // Pension 상품인 경우
            return productDetailMapper.selectPensionDetailById(productId, userId);
        } else {
            // 알 수 없는 접두사일 경우 예외 처리
            throw new IllegalArgumentException("Invalid product ID prefix: " + prefix);
        }
    }


    @Override
    @Async
    @Transactional
    public void incrementProductViewCountAsync(String productId) {
        char prefix = productId.charAt(0);

        if (prefix == 'D') {
            productDetailMapper.incrementDepositViewCount(productId);
        } else if (prefix == 'S') {
            productDetailMapper.incrementSavingsViewCount(productId);
        } else if (prefix == 'F') {
            productDetailMapper.incrementFundViewCount(productId);
        } else if (prefix == 'X') {
            productDetailMapper.incrementForexViewCount(productId);
        } else if (prefix == 'A' || prefix == 'C') {
            productDetailMapper.incrementPensionViewCount(productId);
        }
    }

}
