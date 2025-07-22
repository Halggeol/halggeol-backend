package com.halggeol.backend.scrap.service;

import com.halggeol.backend.scrap.dto.ScrapRequestDTO;

public interface ScrapService {
    int addScrapProduct(int userId, ScrapRequestDTO requestDto);
    int removeScrapProduct(int userId, ScrapRequestDTO requestDto);
}
