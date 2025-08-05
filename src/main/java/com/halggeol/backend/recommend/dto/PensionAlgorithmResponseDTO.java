package com.halggeol.backend.recommend.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PensionAlgorithmResponseDTO {
    private String id;
    private String name;
    private Float rate;  // 연 수익률 or 금리
    private String pensionKind;
    private String pensionType;
    private Float minGuaranteeRate; // 최소 보장 금리
    private Integer risk;
    private Integer fSector; // 금융권 구분 (1: 1금융권, 2: 2금융권)
    private Integer saveTerm; // 저축 기간 (예: "1년", "3년", "5년")
    private String rateType; // 금리 타입 (예: "단리", "복리")
    private Long minLimit; // 최소 가입금액
    private Long maxLimit; // 최고 한도
    private Double TER; // 총 보수 비율 (Total Expense Ratio)
}
