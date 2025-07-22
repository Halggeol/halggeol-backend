package com.halggeol.backend.products.controller;


import com.halggeol.backend.products.service.ProductRegretService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/products/recommend")
@RequiredArgsConstructor
public class ProductRegretController {

    private final ProductRegretService productRegretService;

    @GetMapping("/")
    public Object getRegretRecommendTop5() {
        return productRegretService.getRegretRecommendTop5();
    }
}
