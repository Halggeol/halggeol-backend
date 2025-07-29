package com.halggeol.backend.recommend.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DepositAlgorithmResponseDTO {

    private String id;
    private String name;
    private Float rate; // 기본 금리
    private Float primeRate; // 최고 우대금리

    private Long maxLimit; // 최고 한도
    private Integer joinDeny; // 가입 제한
    private String minimumCost; // 최소 가입금액

    private Integer minSaveTerm; // 최소 저축 기간
    private Integer maxSaveTerm; // 최대 저축 기간
}
