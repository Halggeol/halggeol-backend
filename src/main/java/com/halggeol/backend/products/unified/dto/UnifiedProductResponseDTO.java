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
public class UnifiedProductResponseDTO {

    // 전체 상품 리스트 조회 시 보여줄 데이터 공통 필드
    private String productId; // 상품 ID
    private String name; // 상품명
    private String company; // 금융사
    private String tag1; // 태그
    private String tag2;
    private String tag3;
    private String title; // 금융 상품 리스트 - 상품 카드 - 오른쪽 위 타이틀
    private String subTitle; // 금융 상품 리스트 - 상품 카드 - 오른쪽 아래 타이틀
}
