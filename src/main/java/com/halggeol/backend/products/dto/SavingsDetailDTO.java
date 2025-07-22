package com.halggeol.backend.products.dto;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class SavingsDetailDTO {
    private String name;
    private Double rate;
    private Double primeRate; // 최고 우대 금리

    private String joinWay; // 가입방법

    private LocalDateTime endDate; // 공시 종료일

    private Long maxLimit; // 최고 한도

    private String joinMember; // 가입 대상

    private String joinDeny; // 가입 제한

    private String bonusCondition; // 우대 조건

    private Long saveTerm; // 저축 기간

    private Long minimumCost; // 최소 가입금액

    private String company; // 운용 회사

    private Integer score; // 적합도 점수

    private Integer risk; // 위험도

    private String regLink; // 가입 링크

    private String caution; // 유의 사항

    private Long viewCnt; // 조회수

    private Long scrapCnt; // 관심수

    private Long regretCnt; // 후회수

    private String matchScore;      // ENUM('상','중','하')
    private String advantage;       // 장점 설명
    private String disadvantage;    // 단점 설명
}
