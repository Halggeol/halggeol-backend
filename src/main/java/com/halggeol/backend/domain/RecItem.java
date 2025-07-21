package com.halggeol.backend.domain;

import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.ibatis.type.Alias;

@Alias("RecItemVO")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecItem {
    private int recId;              // 추천 ID (PK)
    private int userId;             // 사용자 ID (FK)
    private LocalDateTime recDate;      // 추천 날짜

    private List<RecommendProduct> recommendedProducts;

    private LocalDateTime anlzDate;
}
