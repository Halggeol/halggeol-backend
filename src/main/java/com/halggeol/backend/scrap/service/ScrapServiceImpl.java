package com.halggeol.backend.scrap.service;

import static com.halggeol.backend.common.ProductPrefixHandler.handleProductByConsumer;

import com.halggeol.backend.logs.service.LogService;
import com.halggeol.backend.scrap.domain.Scrap;
import com.halggeol.backend.scrap.dto.ScrapRequestDTO;
import com.halggeol.backend.scrap.mapper.ScrapMapper;
import com.halggeol.backend.security.domain.CustomUser;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ScrapServiceImpl implements ScrapService {

    private final ScrapMapper scrapMapper;
    private final LogService logService;

    @Override
    @Transactional
    public void addScrapProduct(@AuthenticationPrincipal CustomUser user, ScrapRequestDTO requestDto) {

        String productId = requestDto.getProductId();

        incrementProductScrapCountAsync(productId);

        Scrap scrap = Scrap.builder()
            .userId(user.getUser().getId())
            .productId(productId)
            .build();

        logService.buildLog("add_scrap", productId, user.getUser().getId());
        scrapMapper.insertUserScrap(scrap);
    }

    @Override
    @Transactional
    public void removeScrapProduct(@AuthenticationPrincipal CustomUser user, ScrapRequestDTO requestDto) {
        String productId = requestDto.getProductId();

        decrementProductScrapCountAsync(productId);
        logService.buildLog("remove_scrap", productId, user.getUser().getId());
        scrapMapper.deleteUserScrap(user.getUser().getId(), productId);
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

    @Override
    public ResponseEntity<?> getScrappedProducts(@AuthenticationPrincipal CustomUser user, List<String> types,
        String sort) {
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not authenticated");
        }
        return ResponseEntity.ok().body(scrapMapper.selectScrappedProducts(user.getUser().getId(), types, sort));
    }

}
