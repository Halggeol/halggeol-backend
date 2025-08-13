package com.halggeol.backend.products.mapper;

import com.halggeol.backend.products.dto.DepositDetailResponseDTO;
import com.halggeol.backend.products.dto.ForexDetailResponseDTO;
import com.halggeol.backend.products.dto.FundDetailResponseDTO;
import com.halggeol.backend.products.dto.PensionDetailResponseDTO;
import com.halggeol.backend.products.dto.SavingsDetailResponseDTO;
import com.halggeol.backend.products.dto.UserSpecificDataResponseDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ProductDetailMapper<T> {

//    Deposit selectDepositDetailById(String id);
    DepositDetailResponseDTO selectBaseDepositDetailById(@Param("depositId") String depositId);
    UserSpecificDataResponseDTO selectUserSpecificDataForDeposit(@Param("userId") String userId, @Param("productId") String productId);

    SavingsDetailResponseDTO selectBaseSavingsDetailById(@Param("savingsId") String savingsId);
    UserSpecificDataResponseDTO selectUserSpecificDataForSavings(@Param("userId") String userId, @Param("productId") String productId);

    FundDetailResponseDTO selectBaseFundDetailById(@Param("fundId") String fundId);
    UserSpecificDataResponseDTO selectUserSpecificDataForFund(@Param("userId") String userId, @Param("productId") String productId);

    ForexDetailResponseDTO selectBaseForexDetailById(@Param("forexId") String forexId);
    UserSpecificDataResponseDTO selectUserSpecificDataForForex(@Param("userId") String userId, @Param("productId") String productId);

    PensionDetailResponseDTO selectBasePensionDetailById(@Param("pensionId") String pensionId);
    UserSpecificDataResponseDTO selectUserSpecificDataForPension(@Param("userId")String userId, @Param("productId") String productId);
    
    // 조회수 증가
    void incrementDepositViewCount(@Param("productId") String productId);
    void incrementSavingsViewCount(@Param("productId") String productId);
    void incrementFundViewCount(@Param("productId") String productId);
    void incrementForexViewCount(@Param("productId") String productId);
    void incrementPensionViewCount(@Param("productId") String productId);

    void updateProductStatus(
        @Param("userId") Integer userId,
        @Param("productId") String productId,
        @Param("productStatus") String productStatus
    );

    String selectProductStatus(@Param("userId") Integer userId, @Param("productId") String productId);
}
