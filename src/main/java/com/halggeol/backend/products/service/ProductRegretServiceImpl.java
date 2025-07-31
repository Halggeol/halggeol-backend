package com.halggeol.backend.products.service;

import com.halggeol.backend.products.dto.RegretRankingResponseDTO;
import com.halggeol.backend.products.mapper.ProductRegretMapper;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductRegretServiceImpl implements ProductRegretService{

    private final ProductRegretMapper mapper;
    @Override
    public ResponseEntity<?> getRegretRecommendTop5() {
        List<RegretRankingResponseDTO> regretRanking = mapper.getRegretRanking();
        if (regretRanking.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(regretRanking);
    }
}
