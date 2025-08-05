package com.halggeol.backend.domain;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.ibatis.type.Alias;

@Alias("SavingsVO")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Savings {

    private String id;

    private String name;

    private Double rate; // 저축 금리

    private Double primeRate; // 최고 우대금리

    private String joinWay; // 가입방법

    private LocalDateTime endDate; // 공시 종료일

    private Long maxLimit; // 최고 한도

    private String joinMember; // 가입 대상

    private String joinDeny; // 가입 제한

    private String bonusCondition; // 우대 조건

    private String rateType; // 저축 금리 유형

    private String saveType; // 적립 유형

    private String company; // 운용 회사

    private Integer score; // 적합도 점수

    private Integer risk; // 위험도

    private String regLink; // 가입 링크

    private String caution; // 유의사항

    private Long viewCnt; // 조회수

    private Long scrapCnt; // 관심수

    private Long regretCnt; // 후회수

    private Double algoCode; // 알고리즘 분류값

    private Integer minSaveTerm; // 최소 저축 기간

    private Integer maxSaveTerm; // 최대 저축 기간

    private Double yieldScore; // 수익성 점수

    private Double riskScore; // 위험성 점수

    private Double costScore; // 비용 점수

    private Double liquidityScore; // 유동성 점수

    private Double complexityScore; // 복잡성 점수

    private Integer fSector; // 금융권 구분 (1: 1금융권, 2: 2금융권)

    private String description; // 상품 설명
}
