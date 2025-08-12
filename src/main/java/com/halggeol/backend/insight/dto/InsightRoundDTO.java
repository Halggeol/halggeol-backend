package com.halggeol.backend.insight.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InsightRoundDTO {
    private int round;
    private List<String> productIds;
    private LocalDateTime recDate;
}
