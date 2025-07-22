package com.halggeol.backend.products.service;

import com.halggeol.backend.domain.Deposit;
import com.halggeol.backend.products.dto.DepositDetailResponseDTO;
import org.springframework.transaction.annotation.Transactional;

public interface ProductDetailService {

    @Transactional(readOnly = true) // 읽기 전용 트랜잭션 설정 (성능 최적화 및 데이터 변경 방지)
    DepositDetailResponseDTO getDepositDetailById(String depositId, String userId);
}
