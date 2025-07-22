package com.halggeol.backend.products.service;

import com.halggeol.backend.domain.Deposit;
import com.halggeol.backend.products.dto.DepositDetailResponseDTO;
import com.halggeol.backend.products.mapper.ProductDetailMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductDetailServiceImpl implements ProductDetailService {

    private final ProductDetailMapper productDetailMapper;

    @Override
    @Transactional(readOnly = true)
    public DepositDetailResponseDTO getDepositDetailById(String depositId, String userId) {

        return productDetailMapper.selectDepositDetailById(depositId, userId);
    }


}
