package com.halggeol.backend.scrap.service;

import com.halggeol.backend.scrap.domain.Scrap;
import com.halggeol.backend.scrap.dto.ScrapRequestDTO;
import com.halggeol.backend.scrap.mapper.ScrapMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ScrapServiceImpl implements ScrapService {
    private final ScrapMapper scrapMapper;

    @Override
    @Transactional

    public int addScrapProduct(int userId, ScrapRequestDTO requestDto) {
        String productId = requestDto.getProductId();

        Scrap scrap = Scrap.builder()
            .userId(userId)
            .productId(productId)
            .build();


        scrapMapper.insertUserScrap(scrap);

        // Todo: 상품의 관심수 증가 로직 필요

        return 0;
    }

    @Override
    @Transactional
    public int removeScrapProduct(int userId, ScrapRequestDTO requestDto) {
        String productId = requestDto.getProductId();
        scrapMapper.deleteUserScrap(userId, productId);
        return 0;
    }
}
