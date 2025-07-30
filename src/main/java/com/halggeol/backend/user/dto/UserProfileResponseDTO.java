package com.halggeol.backend.user.dto;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserProfileResponseDTO {
    private String email;
    private String name; // 유저 실제이름
    private String phone; // 전화번호
    private LocalDateTime birth; // 생일
    private int risk; // 위험도
    private int userKlg; // 금융 이해도
    private LocalDateTime regDate; // 가입일자
    private LocalDateTime klgRenewDate; // 금융 이해도 검사일
    private LocalDateTime riskRenewDate; // 위험도/투자 성향 검사일
    private LocalDateTime docuRenewDate; // 개인정보 동의일자

    public Map<String, Object> toMap() {
        Map<String, Object> map = new LinkedHashMap<>();

        map.put("email", email);
        map.put("name", name);
        map.put("phone", phone);
        map.put("birth", dateToString(birth));
        map.put("risk", risk);
        map.put("userKlg", userKlg);
        map.put("regDate", dateToString(regDate));
        map.put("klgRenewDate", dateToString(klgRenewDate));
        map.put("riskRenewDate", dateToString(riskRenewDate));
        map.put("docuRenewDate", dateToString(docuRenewDate));

        return map;
    }

    private String dateToString(LocalDateTime date) {
        return date != null ? date.toLocalDate().toString() : "";
    }
}
