package com.halggeol.backend.products.unified.elasticsearch.controller;

import com.halggeol.backend.products.unified.elasticsearch.service.SearchLogService;
import com.halggeol.backend.security.domain.CustomUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/search-logs")
@RequiredArgsConstructor
@Slf4j
public class SearchLogController {
    private final SearchLogService searchLogService;

    // 최근 검색어 조회
    @GetMapping("/recent")
    public ResponseEntity<?> getRecentSearches(
        @AuthenticationPrincipal CustomUser user
    ){
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();        }
        Integer userId = user.getUser().getId();
        return ResponseEntity.ok(searchLogService.getRecentSearches(userId));
    }

    // 최근 검색어 개별 삭제
    @DeleteMapping("/recent/{keyword}")
    public ResponseEntity<?> deleteRecentSearch(
        @PathVariable String keyword,
        @AuthenticationPrincipal CustomUser user){
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        Integer userId = user.getUser().getId();
        searchLogService.deleteRecentSearch(keyword, userId);
        return ResponseEntity.noContent().build();
    }

    // 최근 검색어 전체 삭제
    @DeleteMapping("/recent/all")
    public ResponseEntity<?> deleteAllSearches(@AuthenticationPrincipal CustomUser user){
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();        }
        Integer userId = user.getUser().getId();
        searchLogService.deleteAllRecentSearches(userId);
        return ResponseEntity.noContent().build();
    }

    // 인기 검색어 조회
    @GetMapping("/popular")
    public ResponseEntity<?> getPopularSearches(){
        return ResponseEntity.ok(searchLogService.getPopularSearches());
    }

}
