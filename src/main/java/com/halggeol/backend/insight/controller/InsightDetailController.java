package com.halggeol.backend.insight.controller;

import com.halggeol.backend.insight.dto.InsightDetailResponseDTO;
import com.halggeol.backend.insight.dto.RegretSurveyRequestDTO;
import com.halggeol.backend.insight.service.InsightDetailService;
import com.halggeol.backend.security.domain.CustomUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/insight/details")
@RequiredArgsConstructor
public class InsightDetailController {
    private final InsightDetailService insightDetailService;

    @GetMapping("")
    public ResponseEntity<?> getInsightDetail(
        @RequestParam Integer round,
        @RequestParam String productId,
        @AuthenticationPrincipal CustomUser user) {
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        try {
            InsightDetailResponseDTO response = insightDetailService.getInsightDetail(round, productId, user);
            if (response == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PatchMapping("")
    public ResponseEntity<Void> updateRegretSurvey(
        @AuthenticationPrincipal CustomUser user,
        @RequestBody RegretSurveyRequestDTO request) {
        if(user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        insightDetailService.updateRegretSurvey(user, request);
        return ResponseEntity.ok().build();
    }
}