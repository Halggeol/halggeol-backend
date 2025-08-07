package com.halggeol.backend.security.domain;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.ibatis.type.Alias;

@Alias("UserVO")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    private int id; // 유저 id 자동생성 1부터 시작
    private String email; // 유저 이메일
    private String name; // 유저 실제이름
    private String password; // 비밀번호 security 암호화되어있음
    private String phone; // 전화번호
    private LocalDateTime birth; // 생일
    private int risk; // 위험도(1~6) 높을수록 안정적인 상품 선호
    private int userKlg; // 금융 이해도
    private float algoCode; // 알고리즘 분류값
    private LocalDateTime regDate; // 가입일자
    private LocalDateTime klgRenewDate; // 금융 이해도 검사일
    private LocalDateTime riskRenewDate; // 위험도/투자 성향 검사일
    private LocalDateTime docuRenewDate; // 개인정보 동의일자
    private Double yieldScore; // 수익성 점수
    private Double riskScore; // 위험성 점수
    private Double costScore; // 비용 점수
    private Double liquidityScore; // 유동성 점수
    private Double complexityScore; // 복잡성 점수
    private String insightCycle; // 인사이트 주기
}
