package com.halggeol.backend.domain;


import java.util.Date;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "user_action_logs")
@Getter
@Setter
public class UserActionLog {
    @Id
    private String id;

    private Integer userId;
    private String actionType; // 예: "view", "scrap", "regret"
    private String productId; // 관련된 상품 ID
    private Date timestamp; // 액션 발생 시간

    private Map<String, Object> additionalData; // 추가 데이터 (예: 상품 정보, 추천 알고리즘 결과 등)
}
