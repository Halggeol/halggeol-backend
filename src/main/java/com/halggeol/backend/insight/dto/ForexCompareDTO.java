package com.halggeol.backend.insight.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Data
// 외환 데이터 흐름 3
// 과거와 현재 환율 비교 (최종 데이터)
public class ForexCompareDTO {
    private int round;                // n회차
    private String productName;       // 외화 상품명 (예 : Sol트래블 외화예금)
    private String curUnit;           // 통화 (예: USD)
    private BigDecimal pastRate;      // 추천일 기준 환율
    private BigDecimal currentRate;   // 현재 기준 환율
    private String recDate;           // 추천일자
    private BigDecimal diff;          // 차이 (현재 - 과거)
    private BigDecimal diffPercent;   // 퍼센트 차이
}

