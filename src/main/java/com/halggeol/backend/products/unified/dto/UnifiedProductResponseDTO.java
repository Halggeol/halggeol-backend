package com.halggeol.backend.products.unified.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UnifiedProducResponsetDTO {

    // 전체 상품 리스트 조회 시 보여줄 데이터 공통 필드
    private String productId; // 상품 ID
    private String name; // 상품명
    private String company; // 금융사
    private String type; // 상품 유형
    private Double rate; // 금리
}
