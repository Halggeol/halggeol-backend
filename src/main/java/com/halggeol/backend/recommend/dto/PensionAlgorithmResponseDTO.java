package com.halggeol.backend.recommend.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PensionAlgorithmResponseDTO {
    private String id;
    private String name;
    private Float rate;
    private String pensionKind;
    private String pensionType;
    private Float minGuaranteeRate; // 최소 보장 금리
    private Float lastYearProfit;
    private Integer risk;
}
