package com.halggeol.backend.insight.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
// 한국수출입은행 open api
public class ExchangeRateDTO {
    private String curUnit;      // 통화 코드 (USD, EUR 등)
    private String curNm;        // 통화명
    private BigDecimal dealBasR; // 기준 환율
    private String baseDate;     // 기준 날짜
}
