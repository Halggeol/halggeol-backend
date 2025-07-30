package com.halggeol.backend.products.controller;

import com.halggeol.backend.products.dto.ProductIdRequestDTO;
import com.halggeol.backend.products.service.ProductDetailService;
import com.halggeol.backend.security.domain.CustomUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/products/detail")
@RequiredArgsConstructor
public class ProductDetailController {
    private final ProductDetailService productDetailService;

    @GetMapping("/{productId}")
    public ResponseEntity<?> getProductDetailById( // 반환 타입이 달라질 수 있으므로 와일드카드 사용
        @PathVariable String productId,
        @AuthenticationPrincipal CustomUser user) {
        try {
            Object response = productDetailService.getProductDetailById(productId, user);
            if (response == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{productId}/status")
    public ResponseEntity<?> checkRecommendProductStatus(
        @AuthenticationPrincipal CustomUser user,
        @PathVariable String productId) {
        try {
            Object response = productDetailService.checkRecommendProductStatus(user, productId);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    // TODO: Insight 충돌이 무서워 일단 ProductDetail에서 구현합니다.
    @PatchMapping("")
    public ResponseEntity<Void> updateProductToRegret(
        @AuthenticationPrincipal CustomUser user,
        @RequestBody ProductIdRequestDTO productIdRequestDTO) {
        try {
            productDetailService.updateProductToRegret(user, productIdRequestDTO.getId());
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
