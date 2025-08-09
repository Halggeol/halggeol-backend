package com.halggeol.backend.insight.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InsightDTO {
    private String productId; //상품 ID
    private String name; //상품 이름
    private String company; //회사
    private String type; //상품 종류 (fund,saving,pension...등)
    private int round;
}
