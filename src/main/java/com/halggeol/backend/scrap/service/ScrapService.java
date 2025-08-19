package com.halggeol.backend.scrap.service;

import com.halggeol.backend.scrap.dto.ScrapRequestDTO;
import com.halggeol.backend.domain.CustomUser;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;

public interface ScrapService {
    @Transactional
    void addScrapProduct(@AuthenticationPrincipal CustomUser user, ScrapRequestDTO requestDto);

    @Transactional
    void removeScrapProduct(@AuthenticationPrincipal CustomUser user, ScrapRequestDTO requestDto);

    void incrementProductScrapCountAsync(String productId);
    void decrementProductScrapCountAsync(String productId);

    // 관심상품 리스트 조회
    ResponseEntity<?> getScrappedProducts(@AuthenticationPrincipal CustomUser user, List<String> types, String sort);
}
