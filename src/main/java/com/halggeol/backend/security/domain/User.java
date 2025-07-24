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
    private int id;
    private String email;
    private String name; // 유저 실제이름
    private String password;
    private String phone; // 전화번호
    private LocalDateTime birth; // 생일
    private int risk; // 위험도
    private int userKlg; // 금융 이해도
    private float algoCode; // 알고리즘 분류값
    private LocalDateTime regDate; // 가입일자
    private LocalDateTime klgRenewDate; // 금융 이해도 검사일
    private LocalDateTime riskRenewDate; // 위험도/투자 성향 검사일
    private LocalDateTime docuRenewDate; // 개인정보 동의일자
}
