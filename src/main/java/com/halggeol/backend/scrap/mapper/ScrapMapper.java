package com.halggeol.backend.scrap.mapper;

import com.halggeol.backend.domain.Scrap;
import com.halggeol.backend.scrap.dto.ScrappedProductResponseDTO;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ScrapMapper {
    void insertUserScrap(Scrap scrap);
    void deleteUserScrap(@Param("userId") int userId, @Param("productId") String productId);

    // 관심수 증가
    void incrementDepositScrapCount(@Param("productId") String productId);
    void incrementSavingsScrapCount(@Param("productId") String productId);
    void incrementFundScrapCount(@Param("productId") String productId);
    void incrementForexScrapCount(@Param("productId") String productId);
    void incrementPensionScrapCount(@Param("productId") String productId);

    void decrementDepositScrapCount(@Param("productId") String productId);
    void decrementSavingsScrapCount(@Param("productId") String productId);
    void decrementFundScrapCount(@Param("productId") String productId);
    void decrementForexScrapCount(@Param("productId") String productId);
    void decrementPensionScrapCount(@Param("productId") String productId);

    // 관심상품 조회
    List<ScrappedProductResponseDTO> selectScrappedProducts(
        @Param("userId") int userId,
        @Param("types") List<String> types,
        @Param("sort") String sort);
}
