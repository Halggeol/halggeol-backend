package com.halggeol.backend.products.controller;

import com.halggeol.backend.products.dto.ProductStatusRequestDTO;
import com.halggeol.backend.products.service.ProductDetailService;
import com.halggeol.backend.domain.CustomUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
            String response = productDetailService.checkRecommendProductStatus(user, productId);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PatchMapping("")
    public ResponseEntity<Void> updateProductStatus(
        @AuthenticationPrincipal CustomUser user,
        @RequestBody ProductStatusRequestDTO productStatusRequestDTO) {
        try {
            productDetailService.updateProductStatus(user, productStatusRequestDTO.getId(), productStatusRequestDTO.getStatus());
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
