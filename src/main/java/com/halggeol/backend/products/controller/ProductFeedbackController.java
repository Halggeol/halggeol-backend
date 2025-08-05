package com.halggeol.backend.products.controller;


import com.halggeol.backend.products.dto.ProductFeedbackRequestDTO;
import com.halggeol.backend.products.service.ProductFeedbackService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/products/feedback")
@RequiredArgsConstructor
public class ProductFeedbackController {

    private final ProductFeedbackService productFeedbackService;

    @PostMapping
    public ResponseEntity<?> createFeedback(@RequestBody ProductFeedbackRequestDTO feedbackDTO) {
        // 피드백 생성 로직을 여기에 구현
        // 예: productFeedbackService.createFeedback(feedbackDTO);
        // 요청 바디에서 피드백 정보를 받아와야 합니다. ProductFeedbackRequestDTO를 사용할 예정
        return productFeedbackService.createFeedback(feedbackDTO);
    }
}
