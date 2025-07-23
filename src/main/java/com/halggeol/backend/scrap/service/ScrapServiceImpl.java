package com.halggeol.backend.scrap.service;

import static com.halggeol.backend.common.ProductPrefixHandler.handleProductByConsumer;

import com.halggeol.backend.scrap.domain.Scrap;
import com.halggeol.backend.scrap.dto.ScrapRequestDTO;
import com.halggeol.backend.scrap.mapper.ScrapMapper;
import java.util.function.Consumer;
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

        handleProductByConsumer(
            productId,
            scrapMapper::incrementDepositScrapCount,
            scrapMapper::incrementSavingsScrapCount,
            scrapMapper::incrementFundScrapCount,
            scrapMapper::incrementForexScrapCount,
            scrapMapper::incrementPensionScrapCount);
    }

    @Override
    @Async
    @Transactional
    public void decrementProductScrapCountAsync(String productId) {
        handleProductByConsumer(
            productId,
            scrapMapper::decrementDepositScrapCount,
            scrapMapper::decrementSavingsScrapCount,
            scrapMapper::decrementFundScrapCount,
            scrapMapper::decrementForexScrapCount,
            scrapMapper::decrementPensionScrapCount);
    }


}
