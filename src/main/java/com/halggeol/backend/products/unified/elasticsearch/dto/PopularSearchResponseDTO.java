package com.halggeol.backend.products.unified.elasticsearch.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
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

    @JsonFormat(shape = JsonFormat.Shape.STRING) // 초 단위 -> 날짜 형식
    private Instant lastSearchTime;
}
