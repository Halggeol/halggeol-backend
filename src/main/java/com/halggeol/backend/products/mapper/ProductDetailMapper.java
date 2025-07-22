package com.halggeol.backend.products.mapper;

import com.halggeol.backend.domain.Deposit;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ProductDetailMapper<T> {

    Deposit selectDepositDetailById(String id);
}
