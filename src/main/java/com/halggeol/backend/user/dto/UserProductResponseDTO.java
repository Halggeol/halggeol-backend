package com.halggeol.backend.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserProductResponseDTO {
    private String productId;
    private String name;
    private String company;

    // 리스트 상품 좌측 상단 태그
    // 예금/적금: 최소/최대 가입 기간 (min_save_term, max_save_term)
    // 펀드: 카테고리, 테마 (category, theme)
    // 연금: 연금 유형, 연금 종류 (pension_type, pension_kind)
    // 외화: 통화 종류 (currency)
    private String tag1;

    // 리스트 상품 좌측 중단 태그
    // 예금/적금: 최소/최대 가입 기간 (min_save_term, max_save_term)
    // 펀드: 테마, 경고 등급 (theme, investment_warning_grade)
    // 연금: 연금 종류, 위험 등급 (pension_kind, risk)
    // 외화: NULL
    private String tag2;

    // 리스트 상품 좌측 하단 태그
    // 예금/적금: NULL
    // 펀드: 경고 등급 (investment_warning_grade)
    // 연금: 위험 등급 (risk)
    // 외화: NULL
    private String tag3;

    // 리스트 상품 우측 상단 메인 정보
    // 예금/적금/외화/펀드: 우대 금리 (prime_rate) 또는 수익률 (rate)
    // 연금: 안정형은 수익률(rate), 공격형은 가격 변동률(pension_price_movement)
    private String title;

    // 리스트 상품 우측 하단 보조 정보
    // 예금/적금/외화: 기본 금리 (rate) 또는 우대 수수료율 (rate_give_way)
    // 펀드: NULL
    // 연금: 안정형은 최저 보증 이율 (min_guarantee_rate), 공격형은 NULL
    private String subTitle;

    // SQL 쿼리에 없는 필드입니다. 추가 정보가 필요합니다.
    private int amount;
}