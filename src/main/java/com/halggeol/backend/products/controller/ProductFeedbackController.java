package com.halggeol.backend.products.controller;


import com.halggeol.backend.products.service.ProductFeedbackService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/products/feedback")
@RequiredArgsConstructor
public class ProductFeedbackController {

    private final ProductFeedbackService productFeedbackService;
}
