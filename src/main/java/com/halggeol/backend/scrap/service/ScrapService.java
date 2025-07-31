package com.halggeol.backend.scrap.service;

import com.halggeol.backend.scrap.dto.ScrapRequestDTO;
import com.halggeol.backend.security.domain.CustomUser;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;

public interface ScrapService {
    @Transactional
    void addScrapProduct(@AuthenticationPrincipal CustomUser user, ScrapRequestDTO requestDto);

    @Transactional
    void removeScrapProduct(@AuthenticationPrincipal CustomUser user, ScrapRequestDTO requestDto);

    void incrementProductScrapCountAsync(String productId);
    void decrementProductScrapCountAsync(String productId);
}
