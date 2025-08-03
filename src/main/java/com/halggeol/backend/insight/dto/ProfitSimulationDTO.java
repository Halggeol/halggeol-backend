package com.halggeol.backend.insight.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProfitSimulationDTO {
    private String date;
    private Double profit; // 추천 상품 수익
    private Long asset;

    private Long lostAsset; // 놓친 전체 재산
    private Long lostInvestment; // 놓친 이자
}
