package com.halggeol.backend.insight.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;
import lombok.Data;

import java.math.BigDecimal;

@Data
// 과거와 현재 환율 비교 (최종 데이터)
public class ForexCompareDTO {
    private Long asset;               // 추천일 기준 자산
    private String curUnit;           // 통화 (예: USD)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate recDate;           // 추천일자
    private BigDecimal pastRate;      // 추천일 기준 환율
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate anlzDate;       // 회고 날짜
    private BigDecimal currentRate;   // 회고 기준 환율
    private BigDecimal diff;          // 차이 (현재 - 과거)
    private BigDecimal diffPercent;   // 퍼센트 차이
}