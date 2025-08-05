package com.halggeol.backend.insight.mapper;

import com.halggeol.backend.insight.dto.ForexCompareDTO;
import com.halggeol.backend.insight.dto.InsightDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Mapper
public interface InsightMapper {
    //특정 월/년도에 놓친 상위 3개 상품 조회
    List<InsightDTO> getTop3MissedProducts(@Param("month") int month, @Param("year") int year);

    //펀드 관련 인사이트 조회
    List<InsightDTO> getFundInsight();

    //공격형 연금 상품 인사이트 조회
    List<InsightDTO> getAggressivePensionInsight();

    // 특정 상품의 특정 날짜 환율 가져오기 (ex : 과거 환율 데이터가 DB에 저장되어 있을 때 사용)
    BigDecimal getForexRateOnDate(@Param("productId") String productId,
                                  @Param("recDate") LocalDate recDate,
                                  @Param("currency") String currency);

    // userId로 추천+회고한 외환 상품의 과거 환율 정보 가져오기 (ex : 해당 사용자가 추천받았지만 가입하지 않은 모든 외환 상품 ) -> 해결
//    List<RegretItemDTO> getForexRegretItems(@Param("userId") Long userId);

    // forex 테이블에서 product_id로 거래가능통화 조회
//    String getForexCurrencyByProductId(@Param("productId") String productId);

    // productId로 외환 상품명 조회
//    String getForexProductNameById(@Param("productId") String productId);

    // 특정 날짜 이전의 가장 최근 환율 조회
    BigDecimal getLatestForexRateBeforeDate(
            @Param("productId") String productId,
            @Param("currency") String currency,
            @Param("recDate") LocalDate recDate
    );

    // ex : 2024/3/15일에 추천 받았던 외환 상품들만 확인
//    List<RegretItemDTO> getForexRegretItemsByDate(@Param("userId") Long userId,
//                                                  @Param("date") LocalDate date);

    // userId로 모든 recId별 ForexCompareDTO 리스트 조회
//    List<ForexCompareDTO> getForexCompareListByUserId(@Param("userId") Long userId);
}
