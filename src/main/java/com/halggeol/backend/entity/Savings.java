package com.halggeol.backend.entity;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    private Date endDate; // 공시 종료일

    private Long maxLimit; // 최고 한도

    private String joinMember; // 가입 대상

    private String joinDeny; // 가입 제한

    private String bonusCondition; // 우대 조건

    private Long saveTerm; // 저축 기간

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
}