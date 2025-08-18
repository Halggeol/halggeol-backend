package com.halggeol.backend.insight.dto;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InsightRoundDTO {
    private int round;
    private List<String> productIds;
    private LocalDateTime recDate;
}
