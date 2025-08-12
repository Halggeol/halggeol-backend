package com.halggeol.backend.insight.mapper;

import com.halggeol.backend.insight.dto.ExchangeRateDTO;
import com.halggeol.backend.insight.dto.ForexCompareDTO;
import com.halggeol.backend.insight.dto.InsightDTO;
import com.halggeol.backend.insight.dto.InsightRoundDTO;
import com.halggeol.backend.security.domain.CustomUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Mapper
public interface InsightMapper {

    List<InsightDTO> getTop3MissedProducts(@Param("round") int round, @Param("userId") int userId);

    // 사용자별 펀드 인사이트 조회
    List<InsightDTO> getFundInsightByUser(@Param("userId") Long userId);

    // 사용자별 공격형 연금 인사이트 조회
    List<InsightDTO> getAggressivePensionInsightByUser(@Param("userId") Long userId);

    // 특정 상품의 특정 날짜 환율 가져오기 (ex : 과거 환율 데이터가 DB에 저장되어 있을 때 사용)
    BigDecimal getForexRateOnDate(@Param("productId") String productId,
                                  @Param("recDate") LocalDate recDate,
                                  @Param("currency") String currency);


    // 특정 날짜 이전의 가장 최근 환율 조회
    BigDecimal getLatestForexRateBeforeDate(
            @Param("productId") String productId,
            @Param("currency") String currency,
            @Param("recDate") LocalDate recDate
    );

    //환율 OPEN API 스케줄러 코드 존재 여부 확인
    boolean existsExchangeRate(@Param("curUnit") String curUnit,
                               @Param("baseDate") String baseDate);

    // 저장 INSERT, 환율 OPEN API 스케줄러 코드
    void insertExchangeRate(ExchangeRateDTO dto);

    // 처음 http://localhost:8080/api/insight 여기 상품 목록 가져오기
    List<InsightRoundDTO> getAllInsightRoundsByUser(Long userId);

    List<InsightDTO> getAllProductsByRoundAndUser(@Param("round") int round, @Param("userId") Long userId);
}
