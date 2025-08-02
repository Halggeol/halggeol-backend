package com.halggeol.backend.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.ibatis.type.Alias;

@Alias("PersonalizedDescriptionVO")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PersonalizedDescription {
    private int id;
    private float algoCode;         // 알고리즘 코드
    private String matchScore;      // ENUM('상','중','하')
    private String advantage;       // 장점 설명
    private String disadvantage;    // 단점 설명
    private String productId;
    private String description;  // 상품 설명
}
