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

    // 리스트 상품 왼쪽 파트
    // 예금, 적금:  min_save_term, max_save_term
    // 펀드 : category, theme, risk
    // 연금: pension_kind, risk
    // 외화: currency
    private String tag1; // 태그
    private String tag2;
    private String tag3;

    // 리스트 상품 오른쪽 파트
    // 예금, 적금: prime_rate, rate
    // 펀드 : rate, fund_price_movement
    // 안정형 연금 : rate, min_guarantee_rate
    // 공격형 연금 : last_year_profit_rate, NULL
    // 외화 : rate, rate_give_way
    private String title;
    private String subTitle;

    private int amount;
}
