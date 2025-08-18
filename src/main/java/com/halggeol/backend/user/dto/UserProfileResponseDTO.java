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
    private LocalDateTime klgRenewDate; // 금융 이해도 검사일
    private LocalDateTime riskRenewDate; // 위험도/투자 성향 검사일
    private LocalDateTime docuRenewDate; // 개인정보 동의일자
    private String insightCycle;

    public Map<String, Object> toMap() {
        Map<String, Object> map = new LinkedHashMap<>();

        map.put("email", email);
        map.put("name", name);
        map.put("phone", formatPhoneString(phone));
        map.put("birth", dateToString(birth));
        map.put("risk", riskToString(risk));
        map.put("userKlg", knowledgeToString(userKlg));
        map.put("klgRenewDate", dateToString(klgRenewDate));
        map.put("riskRenewDate", dateToString(riskRenewDate));
        map.put("docuRenewDate", dateToString(docuRenewDate));
        map.put("insightCycle", insightCycle);

        return map;
    }

    private String dateToString(LocalDateTime date) {
        return date != null ? date.toLocalDate().toString() : "";
    }

    private String riskToString(int risk) {
        return switch (risk) {
            case 1 -> "공격투자형";
            case 2 -> "적극투자형";
            case 3 -> "위험중립형";
            case 4 -> "안전추구형";
            case 5 -> "안정형";
            default -> "";
        };
    }

    private String knowledgeToString(int userKlg) {
        if (0 <= userKlg && userKlg < 7) return "하";
        if (7 <= userKlg && userKlg < 14) return "중";
        if (14 <= userKlg && userKlg <= 20) return "상";
        return "";
    }

    private String formatPhoneString(String phone) {
        if (phone.length() == 10)
            return phone.replaceFirst("(\\d{3})(\\d{3})(\\d{4})", "$1-$2-$3");
        if (phone.length() == 11)
            return phone.replaceFirst("(\\d{3})(\\d{4})(\\d{4})", "$1-$2-$3");
        return phone;
    }
}
