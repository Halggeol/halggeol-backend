package com.halggeol.backend.insight.mapper;

import com.halggeol.backend.insight.dto.InsightDetailResponseDTO;
import com.halggeol.backend.insight.dto.ProfitSimulationDTO;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface InsightDetailMapper {
    InsightDetailResponseDTO getDepositInfo(@Param("round") int round, @Param("productId") String productId, @Param("userId") int userId);
    InsightDetailResponseDTO getSavingsInfo(@Param("round") int round, @Param("productId") String productId, @Param("userId") int userId);
    InsightDetailResponseDTO getConservativePensionInfo(@Param("round") int round, @Param("productId") String productId, @Param("userId") int userId);
    InsightDetailResponseDTO getAggressivePensionInfo(@Param("round") int round, @Param("productId") String productId, @Param("userId") int userId);
    InsightDetailResponseDTO getFundInfo(@Param("round") int round, @Param("productId") String productId, @Param("userId") int userId);
    InsightDetailResponseDTO getForexInfo(@Param("round") int round, @Param("productId") String productId, @Param("userId") int userId);
    List<ProfitSimulationDTO> getProfitSimulation(@Param("round") int round, @Param("productId") String productId, @Param("userId") int userId, @Param("type") String type, @Param("recDate") LocalDate recDate, @Param("anlzDate") LocalDate anlzDate);

    Long getAsset(@Param("id") int id, @Param("date") LocalDate date);
    BigDecimal getExchangeRate(@Param("currency") String currency, @Param("date") LocalDate date);

    void updateRegretInsight(Map<String, Object> params);
    int updateRegretSurvey(Map<String, Object> params);
}
