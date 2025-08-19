package com.halggeol.backend.common.controller;

import com.halggeol.backend.common.service.GeminiService;
import com.halggeol.backend.domain.CustomUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/gemini")
@RequiredArgsConstructor
@Slf4j
public class GeminiController {

    private final GeminiService geminiService;

    @PostMapping("/analyze")
    public ResponseEntity<?> analyzeProduct(
            @RequestBody Object productDetail,
            @AuthenticationPrincipal CustomUser user) {
        try {
            // 상품 데이터 분석하여 advantage, disadvantage, description 생성
            Object analysisResult = geminiService.analyzeProductWithGemini(productDetail, user.getUser());
            return ResponseEntity.ok(analysisResult);
        } catch (Exception e) {
            log.error("Gemini 분석 중 오류 발생", e);
            return ResponseEntity.internalServerError()
                    .body("분석 중 오류가 발생했습니다.");
        }
    }


}