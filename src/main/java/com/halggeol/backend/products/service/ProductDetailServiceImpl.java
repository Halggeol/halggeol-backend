package com.halggeol.backend.products.service;

import com.halggeol.backend.domain.Deposit;
import com.halggeol.backend.products.mapper.ProductDetailMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductDetailServiceImpl implements ProductDetailService {
    private final ProductDetailMapper productDetailMapper;

    @Override
    @Transactional(readOnly = true) // 읽기 전용 트랜잭션 설정 (성능 최적화 및 데이터 변경 방지)
    public Deposit getDepositDetailById(String id) {
        Deposit deposit = productDetailMapper.selectDepositDetailById(id);

        return deposit;
    }

}
