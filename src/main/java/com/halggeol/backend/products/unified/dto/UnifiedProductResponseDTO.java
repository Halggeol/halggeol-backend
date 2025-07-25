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

    // 리스트 상품 왼쪽 파트
    private String tag1; // 태그
    private String tag2;
    private String tag3;

    // 리스트 상품 오른쪽 파트
    private String title; // 금융 상품 리스트 - 상품 카드 - 오른쪽 위 타이틀
    private String subTitle; // 금융 상품 리스트 - 상품 카드 - 오른쪽 아래 타이틀

    // 필터링
    private String type; // 상품 유형
    private Integer fSector; // 은행 (1금융권, 2금융권, 자산운용)
    private Integer saveTerm; // 가입 기간 - 예적금만 존재
    private String minAmount; // 가입 금액 - 적금, 펀드만 존재

}
