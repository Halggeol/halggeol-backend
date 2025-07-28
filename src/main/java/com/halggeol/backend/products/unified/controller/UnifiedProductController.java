package com.halggeol.backend.products.unified.controller;

import com.halggeol.backend.products.unified.dto.UnifiedProductResponseDTO;
import com.halggeol.backend.products.unified.service.UnifiedProductService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class UnifiedProductController {

    private final UnifiedProductService unifiedProductService;

    @GetMapping
    public List<UnifiedProductResponseDTO> getProducts(
        @RequestParam(required = false) String sort, // 정렬 기준
//        @RequestParam(required = false) String keyword, // 검색어 (상품명)
        @RequestParam(required = false) String type, // 상품 유형
        @RequestParam(required = false) Integer fSector, // 은행 (1금융권, 2금융권, 자산운용)
        @RequestParam(required = false) Integer saveTerm, // 가입 기간
        @RequestParam(required = false) String minAmount // 가입 금액
    ) {
        return unifiedProductService.getFilteredProducts(sort, type, fSector, saveTerm, minAmount);
    }

}
