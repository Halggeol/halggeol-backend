package com.halggeol.backend.insight.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProfitSimulationDTO {
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate date;
    private Double profit; // 추천 상품 수익
    private Long asset;

    private Long lostAsset; // 놓친 전체 재산
    private Long lostInvestment; // 놓친 이자
}
