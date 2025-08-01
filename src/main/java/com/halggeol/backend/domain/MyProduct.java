package com.halggeol.backend.domain;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.ibatis.type.Alias;

@Alias("MyProductVO")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MyProduct {
    private int id; // 마이프로덕트 id 자동생성 1부터 시작
    private int mydataId; // 마이데이터 id
    private Integer amount; // 상품 금액(원화) 수집일자의 상품 금액
    private LocalDateTime regDate; // 상품 등록일자
    private LocalDateTime endDate; // 상품 만기일자
    private String productId; // 상품 ID(예금은 D0001, 적금은 S0001, 펀드는 F0001, 외화는 X0001 , 공격형 연금은 A0001, 안정형 연금은 C0001 이런 식)
}
