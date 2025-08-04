package com.halggeol.backend.products.unified.dto;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UnifiedProductRegretRankingResponseDTO implements Serializable {
    private Integer rank;
    private String productId;
    private String productName;
    private Integer risk;

    // rate와 period는 1위 상품에만 값이 있고, 나머지는 NULL이므로 래퍼 타입 사용
    private Double rate;
    private Integer period;
}
