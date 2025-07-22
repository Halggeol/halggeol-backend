package com.halggeol.backend.products.service;

import com.halggeol.backend.products.mapper.ProductDetailMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductDetailServiceImpl implements ProductDetailService {

    private final ProductDetailMapper productDetailMapper;

    @Override
    @Transactional(readOnly = true)
    public Object getProductDetailById(String productId, String userId) {
        
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
            throw new IllegalArgumentException("Invalid product ID prefix: " + prefix + ". Expected one of 'D', 'S', 'F', 'X', 'A', or 'C'.");
        }
    }

}
