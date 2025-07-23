package com.halggeol.backend.common;

import java.util.function.BiFunction;
import java.util.function.Consumer;

public class ProductPrefixHandler {

    // private 생성자로 인스턴스화를 방지하여 유틸리티 클래스임을 명시
    private ProductPrefixHandler() {
    }

    /**
     * 상품 ID 접두사에 따라 해당하는 Consumer<String> 핸들러를 실행합니다.
     * 주로 조회수 증가나 스크랩 수 증감 등 단일 인자(productId)를 받고 반환값이 없는 작업에 사용됩니다.
     *
     * @param productId 상품 ID
     * @param depositHandler 'D' 접두사일 때 실행할 Consumer
     * @param savingsHandler 'S' 접두사일 때 실행할 Consumer
     * @param fundHandler 'F' 접두사일 때 실행할 Consumer
     * @param forexHandler 'X' 접두사일 때 실행할 Consumer
     * @param pensionHandler 'A' 또는 'C' 접두사일 때 실행할 Consumer
     * @throws IllegalArgumentException 유효하지 않은 상품 ID 접두사일 경우
     */
    public static void handleProductByConsumer(String productId,
        Consumer<String> depositHandler,
        Consumer<String> savingsHandler,
        Consumer<String> fundHandler,
        Consumer<String> forexHandler,
        Consumer<String> pensionHandler) {
        if (productId == null || productId.isEmpty()) {
            throw new IllegalArgumentException("Product ID cannot be null or empty.");
        }
        char prefix = productId.charAt(0);
        if (prefix == 'D') {
            depositHandler.accept(productId);
        } else if (prefix == 'S') {
            savingsHandler.accept(productId);
        } else if (prefix == 'F') {
            fundHandler.accept(productId);
        } else if (prefix == 'X') {
            forexHandler.accept(productId);
        } else if (prefix == 'A' || prefix == 'C') {
            pensionHandler.accept(productId);
        } else {
            throw new IllegalArgumentException("Invalid product ID prefix: " + prefix);
        }
    }

    /**
     * 상품 ID 접두사에 따라 해당하는 BiFunction<String, String, T> 핸들러를 실행하고 결과를 반환합니다.
     * 주로 상세 정보 조회 등 두 개의 인자(productId, userId)를 받고 특정 타입 T를 반환하는 작업에 사용됩니다.
     *
     * @param productId 상품 ID
     * @param userId 사용자 ID
     * @param depositHandler 'D' 접두사일 때 실행할 BiFunction
     * @param savingsHandler 'S' 접두사일 때 실행할 BiFunction
     * @param fundHandler 'F' 접두사일 때 실행할 BiFunction
     * @param forexHandler 'X' 접두사일 때 실행할 BiFunction
     * @param pensionHandler 'A' 또는 'C' 접두사일 때 실행할 BiFunction
     * @param <T> 반환 타입
     * @return 핸들러 실행 결과
     * @throws IllegalArgumentException 유효하지 않은 상품 ID 접두사일 경우
     */
    public static <T> T handleProductByBiFunction(String productId, String userId,
        BiFunction<String, String, T> depositHandler,
        BiFunction<String, String, T> savingsHandler,
        BiFunction<String, String, T> fundHandler,
        BiFunction<String, String, T> forexHandler,
        BiFunction<String, String, T> pensionHandler) {
        if (productId == null || productId.isEmpty()) {
            throw new IllegalArgumentException("Product ID cannot be null or empty.");
        }
        char prefix = productId.charAt(0);
        if (prefix == 'D') {
            return depositHandler.apply(productId, userId);
        } else if (prefix == 'S') {
            return savingsHandler.apply(productId, userId);
        } else if (prefix == 'F') {
            return fundHandler.apply(productId, userId);
        } else if (prefix == 'X') {
            return forexHandler.apply(productId, userId);
        } else if (prefix == 'A' || prefix == 'C') {
            return pensionHandler.apply(productId, userId);
        } else {
            throw new IllegalArgumentException("Invalid product ID prefix: " + prefix);
        }
    }
}
