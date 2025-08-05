package com.halggeol.backend.recommend.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ForexAlgorithmResponseDTO {

    private String id;
    private String name;
    private Float rate; // 이율
    private String currency; // 통화 종류
    private Long minLimit; // 최소 가입금액
    private Long maxLimit; // 최고 한도
    private Short taxRefund; // 세금 환급 여부 (0: 없음, 1: 있음)
    private Short protect; // 보호 여부 (0: 없음, 1: 있음)
    private String autoRenew; // 자동 연장 여부 (예: "Y", "N")
    private Short extraDeposit; // 추가 입금 가능 여부 (0: 불가능, 1: 가능)
    private Integer minSaveTerm; // 최소 저축 기간 (개월 단위)
    private Integer maxSaveTerm; // 최대 저축 기간 (개월 단위)
    private Integer risk; // 위험 등급
}
