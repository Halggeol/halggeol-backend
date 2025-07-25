package com.halggeol.backend.products.unified.controller;

import com.halggeol.backend.products.unified.dto.UnifiedProductResponseDTO;
import com.halggeol.backend.products.unified.service.UnifiedProductService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class UnifiedProductController {

    private final UnifiedProductService unifiedProductService;

    @GetMapping
    public List<UnifiedProductResponseDTO> getAllProducts() {
        return unifiedProductService.getAllProducts();
    }

}
