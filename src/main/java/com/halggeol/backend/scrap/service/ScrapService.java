package com.halggeol.backend.scrap.service;

import com.halggeol.backend.scrap.dto.ScrapRequestDTO;
import org.springframework.scheduling.annotation.Async;
import org.springframework.transaction.annotation.Transactional;

public interface ScrapService {
    int addScrapProduct(int userId, ScrapRequestDTO requestDto);
    int removeScrapProduct(int userId, ScrapRequestDTO requestDto);

    void incrementProductScrapCountAsync(String productId);
    void decrementProductScrapCountAsync(String productId);
}
