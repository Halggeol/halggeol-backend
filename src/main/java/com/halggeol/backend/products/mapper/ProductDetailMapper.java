package com.halggeol.backend.products.mapper;

import com.halggeol.backend.domain.Deposit;
import com.halggeol.backend.products.dto.DepositDetailResponseDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ProductDetailMapper<T> {

//    Deposit selectDepositDetailById(String id);
    DepositDetailResponseDTO selectDepositDetailById(@Param("depositId") String depositId, @Param("userId") String userId);
}
