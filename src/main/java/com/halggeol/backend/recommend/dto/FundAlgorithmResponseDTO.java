package com.halggeol.backend.recommend.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FundAlgorithmResponseDTO {
    private String id;
    private String name;
    private Float rate; // 최근 1년 수익률
    private Float fundPrice; // 펀드 가격
    private Float TER; // 총보수
    private String investmentWarningGrade; // 투자경고등급
    private Integer risk; // 위험등급
    private Float upfrontFee; // 선취수수료
    private Float managementFee; // 운용보수
    private String investmentType; // 투자유형
    private Float fundPriceMovement; // 펀드 가격 변동
    private Long minLimit; // 최소 가입금액
    private String category; // 테마
}
