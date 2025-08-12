package com.halggeol.backend.products.unified.elasticsearch.controller;

import com.halggeol.backend.products.unified.elasticsearch.service.ProductSearchService;
import com.halggeol.backend.security.domain.CustomUser;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
@Slf4j
public class ProductSearchController {

    private final ProductSearchService searchService;

    @GetMapping
    public ResponseEntity<?> getProducts(
        @RequestParam(required = false) String sort,
        @RequestParam(required = false) String keyword,
        @RequestParam(required = false) List<Integer> fSectors,
        @RequestParam(required = false) List<String> types,
        @RequestParam(required = false) Integer minAmount,
        @RequestParam(required = false) Integer saveTerm,
        @AuthenticationPrincipal CustomUser user
    ) {
        return searchService.searchProducts(sort, keyword, fSectors, types, minAmount, saveTerm, user);
    }
}
