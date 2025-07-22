package com.halggeol.backend.products.controller;

import com.halggeol.backend.products.service.ProductDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

// Todo: 이후 jwt 되면 모든 userId 를 @AuthenticationPrincipal UserDetails userDetails로 변경
// Todo: 프론트 페이지에서 그래프에 필요한 바디값을 찾아서 mapper 코드에 추가해야됨

@RestController
@RequestMapping("/api/products/detail")
@RequiredArgsConstructor
public class ProductDetailController {
    private final ProductDetailService productDetailService;

    @GetMapping("/{productId}")
    public ResponseEntity<?> getProductDetailById( // 반환 타입이 달라질 수 있으므로 와일드카드 사용
        @PathVariable String productId,
        @RequestParam("userId") String userId) {
        try {
            Object response = productDetailService.getProductDetailById(productId, userId);
            if (response == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
