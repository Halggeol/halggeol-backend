package com.halggeol.backend.scrap.service;

import com.halggeol.backend.scrap.domain.Scrap;
import com.halggeol.backend.scrap.dto.ScrapRequestDTO;
import com.halggeol.backend.scrap.mapper.ScrapMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
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

        incrementProductScrapCountAsync(productId);

        Scrap scrap = Scrap.builder()
            .userId(userId)
            .productId(productId)
            .build();


        scrapMapper.insertUserScrap(scrap);

        return 0;
    }

    @Override
    @Transactional
    public int removeScrapProduct(int userId, ScrapRequestDTO requestDto) {
        String productId = requestDto.getProductId();

        decrementProductScrapCountAsync(productId);
        scrapMapper.deleteUserScrap(userId, productId);
        return 0;
    }

    @Override
    @Async
    @Transactional
    public void incrementProductScrapCountAsync(String productId) {

        char prefix = productId.charAt(0);

        if (prefix == 'D') {
            scrapMapper.incrementDepositScrapCount(productId);
        } else if (prefix == 'S') {
            scrapMapper.incrementSavingsScrapCount(productId);
        } else if (prefix == 'F') {
            scrapMapper.incrementFundScrapCount(productId);
        } else if (prefix == 'X') {
            scrapMapper.incrementForexScrapCount(productId);
        } else if (prefix == 'A' || prefix == 'C') {
            scrapMapper.incrementPensionScrapCount(productId);
        }
    }

    @Override
    @Async
    @Transactional
    public void decrementProductScrapCountAsync(String productId) {

        char prefix = productId.charAt(0);

        if (prefix == 'D') {
            scrapMapper.decrementDepositScrapCount(productId);
        } else if (prefix == 'S') {
            scrapMapper.decrementSavingsScrapCount(productId);
        } else if (prefix == 'F') {
            scrapMapper.decrementFundScrapCount(productId);
        } else if (prefix == 'X') {
            scrapMapper.decrementForexScrapCount(productId);
        } else if (prefix == 'A' || prefix == 'C') {
            scrapMapper.decrementPensionScrapCount(productId);
        }
    }
}
