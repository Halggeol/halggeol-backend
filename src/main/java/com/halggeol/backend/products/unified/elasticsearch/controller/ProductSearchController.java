package com.halggeol.backend.products.unified.elasticsearch.controller;

import com.halggeol.backend.products.unified.elasticsearch.dto.ProductSearchResponseDTO;
import com.halggeol.backend.products.unified.elasticsearch.service.ProductSearchService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<List<ProductSearchResponseDTO>> getProducts(
        @RequestParam(required = false) String sort,
        @RequestParam(required = false) String keyword,
        @RequestParam(required = false) List<Integer> fSectors,
        @RequestParam(required = false) List<String> types,
        @RequestParam(required = false) String minAmount,
        @RequestParam(required = false) Integer saveTerm
    ) {
        log.info("Product search request - sort: {}, keyword: {}, fSector: {}, type: {}, minAmount: {}, saveTerm: {}",
            sort, keyword, fSectors, types, minAmount, saveTerm);
        try {
            List<ProductSearchResponseDTO> results = searchService.searchProducts(sort, keyword,
                fSectors,
                types, minAmount, saveTerm);
            return ResponseEntity.ok(results);
        } catch (Exception e) {
            log.error("Error during getting products search results", e);
            return ResponseEntity.status(500).build();
        }
    }
}
