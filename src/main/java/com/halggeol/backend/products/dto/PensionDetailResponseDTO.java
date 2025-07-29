package com.halggeol.backend.products.dto;

import java.time.LocalDateTime;
import java.util.Date;
import lombok.Data;

@Data
public class PensionDetailResponseDTO {
    private String id;

    private String name;

    private Double rate; // 금리

    private String pensionKind; // 연금 종류

    private Boolean pensionType; // 금리 고정 여부

    private Double minGuaranteeRate; // 최저 보증 이율

    private Double lastYearProfitRate; // 작년 수익률

    private Date endDate; // 공시 종료일

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
    private Boolean isScraped;      // 스크랩 여부
}
