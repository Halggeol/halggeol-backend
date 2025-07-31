package com.halggeol.backend.insight.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
// 외환 데이터 흐름 1
// 사용자가 놓친 상품 추천 (ex : 2024/01/15에 'Sol트래블 외화예금'을 추천 받았는데 가입하지 않음, 그때 USD,EUR 거래 가능 통화)
public class RegretItemDTO {
    private String productId; //외환 상품 ID
    private LocalDate recDate; //추천 일자
    private String currency; //거래 가능 통화 (예 : USD, EUR ...)
    private int round;// n회차
}
