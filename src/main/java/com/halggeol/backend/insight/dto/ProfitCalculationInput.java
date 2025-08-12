package com.halggeol.backend.insight.dto;

import java.time.LocalDate;
import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProfitCalculationInput {
    private Integer round; // 회차
    private LocalDate recDate; // 후회 날짜
    private LocalDate anlzDate; // 분석 날짜
    private String productId;
    private Long maxLimit; // 최대 가입 금액
    private Boolean isCompound; // D,S,C의 단리/복리 확인
    private Integer saveTerm; // C의 가입 기간
    private Float TER; // A, F의 보수비용
    private Float upfrontFee; // F의 선취수수료
    private List<ProfitSimulationDTO> profitSimulations; // 시뮬데이터

    public static ProfitCalculationInput from(InsightDetailResponseDTO baseInfo, List<ProfitSimulationDTO> initialSimulations) {
        return ProfitCalculationInput.builder()
            // baseInfo
            .recDate(baseInfo.getRecDate())
            .anlzDate(baseInfo.getAnlzDate())
            .productId(baseInfo.getId())
            .maxLimit(baseInfo.getMaxLimit())
            .isCompound(baseInfo.getIsCompound())
            .saveTerm(baseInfo.getSaveTerm())
            .TER(baseInfo.getTER())
            .upfrontFee(baseInfo.getUpfrontFee())
            // initialSimulations
            .profitSimulations(initialSimulations)
            .build();
    }
}
