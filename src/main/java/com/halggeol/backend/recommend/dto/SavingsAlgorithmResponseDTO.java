package com.halggeol.backend.recommend.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SavingsAlgorithmResponseDTO {
    private String id;
    private String name;
    private Float rate; // 기본 금리
    private Float primeRate; // 최고 우대금리
    private Long minLimit; // 최소 가입금액
    private Long maxLimit; // 최고 한도
    private String rateType; // 금리 타입 (예: "단리", "복리")
    private Integer joinDeny; // 가입 제한
    private Integer minSaveTerm; // 최소 저축 기간
    private Integer maxSaveTerm; // 최대 저축 기간
    private Integer fSector; // 금융권 구분 (1: 1금융권, 2: 2금융권)
}
