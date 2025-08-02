package com.halggeol.backend.domain;

import java.time.LocalDateTime;
import lombok.*;
import org.apache.ibatis.type.Alias;

@Alias("PensionVO")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Pension {

    private String id;

    private String name;

    private Double rate; // 금리

    private Double pensionPriceMovement; // 연금 가격 변동

    private String pensionKind; // 연금 종류

    private Boolean pensionType; // 금리 고정 여부

    private Double minGuaranteeRate; // 최저 보증 이율

    private LocalDateTime endDate; // 공시 종료일

    private String company; // 운용 회사

    private Integer score; // 적합도 점수

    private Integer risk; // 위험도

    private String regLink; // 가입 링크

    private String caution; // 유의 사항

    private Long viewCnt; // 조회수

    private Long scrapCnt; // 관심수

    private Long regretCnt; // 후회수

    private Double algoCode; // 알고리즘 분류값

    private Double yieldScore; // 수익성 점수

    private Double riskScore; // 위험성 점수

    private Double costScore; // 비용 점수

    private Double liquidityScore; // 유동성 점수

    private Double complexityScore; // 복잡성 점수

    private Integer fSector; // 금융권 구분

    private String description; // 상품 설명

    private Integer saveTerm; // 저축 기간

    private String rateType; // 금리 타입 (예: "단리", "복리")

    private Long minLimit; // 최소 가입금액

    private Long maxLimit; // 최고 한도

    private Double TER; // 총 보수 비율 (Total Expense Ratio)
}

