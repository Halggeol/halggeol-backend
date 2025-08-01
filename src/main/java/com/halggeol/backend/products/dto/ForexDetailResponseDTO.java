package com.halggeol.backend.products.dto;

import java.time.LocalDateTime;
import java.util.Date;
import lombok.Data;

@Data
public class ForexDetailResponseDTO {
    private String id;

    private String name;

    private Double rate; // 금리

    private String description; // 상품 특징

    private String currency; // 거래 가능 통화

    private Date regLimitDate; // 가입 가능 기간

    private String autoRenew; // 자동 연장

    private Boolean extraDeposit; // 추가 입금

    private String rateGiveWay; // 이자 지급 방법

    private Boolean taxRefund; // 세제 혜택

    private Boolean protect; // 예금자보호 여부

    private String company; // 운용 회사

    private Integer score; // 적합도 점수

    private Integer risk; // 위험도

    private String regLink; // 가입 링크

    private String caution; // 유의 사항

    private Integer viewCnt; // 조회수

    private Integer scrapCnt; // 관심수

    private Integer regretCnt; // 후회수

    private String rateType; // 금리 유형

    private Integer maxSaveTerm; // 최대 저축 기간
    private Integer minSaveTerm; // 최소 저축 기간

    private Long minLimit; // 최소 비용
    private Long maxLimit; // 최소 비용

    private String advantage;       // 장점 설명
    private String disadvantage;    // 단점 설명
    private Boolean isScraped;      // 스크랩 여부
}
