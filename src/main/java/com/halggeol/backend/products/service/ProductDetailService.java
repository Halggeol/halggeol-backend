package com.halggeol.backend.products.service;

import com.halggeol.backend.domain.Deposit;
import com.halggeol.backend.products.dto.DepositDetailResponseDTO;
import com.halggeol.backend.products.dto.SavingsDetailResponseDTO;
import org.springframework.transaction.annotation.Transactional;

public interface ProductDetailService {


    @Transactional(readOnly = true)
    Object getProductDetailById(String productId, String userId);
}
