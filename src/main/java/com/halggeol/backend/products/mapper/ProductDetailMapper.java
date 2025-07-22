package com.halggeol.backend.products.mapper;

import com.halggeol.backend.domain.Deposit;
import com.halggeol.backend.products.dto.DepositDetailResponseDTO;
import com.halggeol.backend.products.dto.ForexDetailResponseDTO;
import com.halggeol.backend.products.dto.FundDetailResponseDTO;
import com.halggeol.backend.products.dto.PensionDetailResponseDTO;
import com.halggeol.backend.products.dto.SavingsDetailResponseDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ProductDetailMapper<T> {

//    Deposit selectDepositDetailById(String id);
    DepositDetailResponseDTO selectDepositDetailById(@Param("depositId") String depositId, @Param("userId") String userId);

    SavingsDetailResponseDTO selectSavingsDetailById(@Param("savingsId") String savingsId, @Param("userId") String userId);

    FundDetailResponseDTO selectFundDetailById(@Param("fundId") String fundId, @Param("userId") String userId);

    ForexDetailResponseDTO selectForexDetailById(@Param("forexId") String forexId, @Param("userId") String userId);

    PensionDetailResponseDTO selectPensionDetailById(@Param("pensionId") String pensionId, @Param("userId") String userId);
}
