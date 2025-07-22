package com.halggeol.backend.products.service;

import com.halggeol.backend.products.dto.DepositDetailResponseDTO;
import com.halggeol.backend.products.dto.SavingsDetailResponseDTO;
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
    @Override
    @Transactional(readOnly = true)
    public SavingsDetailResponseDTO getSavingsDetailById(String savingsId, String userId) {
        // 매퍼에서 이미 조인 및 DTO 매핑을 완료하므로, 별도의 변환 없이 반환
        return productDetailMapper.selectSavingsDetailById(savingsId, userId);
    }

}
