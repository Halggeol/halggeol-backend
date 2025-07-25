package com.halggeol.backend.recommend.service;

import com.halggeol.backend.recommend.dto.RecommendResponseDTO;
import com.halggeol.backend.recommend.mapper.RecommendMapper;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RecommendServiceImpl implements RecommendService {

    private final RecommendMapper recommendMapper;

    @Override
    @Transactional
    public List<RecommendResponseDTO> getRecommendProducts(String userId) {
        return recommendMapper.selectRecommendProducts(userId);
    }
}
