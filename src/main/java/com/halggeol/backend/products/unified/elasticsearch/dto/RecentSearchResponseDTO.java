package com.halggeol.backend.products.unified.elasticsearch.dto;

import java.time.Instant;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class RecentSearchResponseDTO {
    private String keyword;
    private Instant timestamp;
    private Integer userId;
}
