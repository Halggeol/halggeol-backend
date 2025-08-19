package com.halggeol.backend.insight.enums;

import lombok.Getter;

@Getter
public enum ProductType {
    DEPOSIT("D", "deposit"),
    SAVINGS("S", "savings"),
    CONSERVATIVE("C", "conservative"),
    AGGRESSIVE("A", "aggressive"),
    FUND("F", "fund"),
    FOREX("X", "forex");

    private final String prefix;
    private final String simulationType;

    ProductType(String prefix, String simulationType) {
        this.prefix = prefix;
        this.simulationType = simulationType;
    }

    public static ProductType fromProductId(String productId) {
        if (productId == null || productId.isEmpty()) {
            throw new IllegalArgumentException("productId가 유효하지 않습니다.");
        }

        char firstChar = productId.charAt(0);
        return switch (firstChar) {
            case 'D' -> DEPOSIT;
            case 'S' -> SAVINGS;
            case 'C' -> CONSERVATIVE;
            case 'A' -> AGGRESSIVE;
            case 'F' -> FUND;
            case 'X' -> FOREX;
            default -> throw new IllegalArgumentException("유효하지 않은 카테고리: " + productId);
        };
    }
}