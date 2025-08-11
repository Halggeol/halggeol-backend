package com.halggeol.backend.products.unified.elasticsearch.controller;

import com.halggeol.backend.products.unified.elasticsearch.service.SearchLogService;
import com.halggeol.backend.security.domain.CustomUser;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/search-logs")
@RequiredArgsConstructor
@Slf4j
public class SearchLogController {
    private final SearchLogService searchLogService;

    @GetMapping("/recent")
    public ResponseEntity<?> getRecentSearches(
        @AuthenticationPrincipal CustomUser user
    ){
        if (user == null) {
            return ResponseEntity.ok(List.of());
        }
        Integer userId = user.getUser().getId();
        return ResponseEntity.ok(searchLogService.getRecentSearches(userId));
    }

    @GetMapping("/popular")
    public ResponseEntity<?> getPopularSearches(){
        return ResponseEntity.ok(searchLogService.getPopularSearches());
    }

}
