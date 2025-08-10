package com.halggeol.backend.products.unified.elasticsearch.dto;

import java.time.Instant;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class PopularSearchResponseDTO {
    private String keyword;
    private Integer count;
    private Instant lastSearchTime;
}
