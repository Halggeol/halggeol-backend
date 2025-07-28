package com.halggeol.backend.products.dto;

import lombok.Data;

@Data
public class FundDetailResponseDTO {
    private String id;

    private String name;

    private Double rate; // 금리

    private Double fundPrice; // 펀드 기준가

    private Double fundPriceMovement; // 기준가 추이 (3개월)

    private Double TER; // 총 보수비용 (수수료)

    private String category; // 유형


    private String theme;    // 테마

    private String investmentWarningGrade; // 투자 위험 등급

    private Double upfrontFee; // 선취 수수료

    private Double managementFee; // 판매 수수료

    private Integer minimumCost; // 최소 비용

    private String target; // 판매 대상


    private String investmentType; // 투자 유형

    private String company; // 운용 회수

    private Integer score; // 적합도 점수

    private Integer risk; // 위험도

    private String regLink; // 가입 링크

    private String caution; // 유의 사항

    private Integer viewCnt; // 조회수

    private Integer scrapCnt; // 관심수

    private Integer regretCnt; // 후회수


    private String matchScore;      // ENUM('상','중','하')
    private String advantage;       // 장점 설명
    private String disadvantage;    // 단점 설명
}
