package com.halggeol.backend.scrap.controller;

import com.halggeol.backend.scrap.dto.ScrapRequestDTO;
import com.halggeol.backend.scrap.service.ScrapService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/scrap")
@RequiredArgsConstructor
public class ScrapController {
    private final ScrapService scrapService;

    @PostMapping
    public ResponseEntity<?> addScrapProduct(
        @RequestParam("userId") int userId, // Todo: 이후 jwt 되면 모든 userId 를 @AuthenticationPrincipal UserDetails userDetails로 변경
        @RequestBody ScrapRequestDTO requestDto) {
        try {
            int result = scrapService.addScrapProduct(userId, requestDto);
            return ResponseEntity.ok(result);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping
    public ResponseEntity<?> removeScrapProduct(
        @RequestParam("userId") int userId, // Todo: 이후 jwt 되면 모든 userId 를 @AuthenticationPrincipal UserDetails userDetails로 변경
        @RequestBody ScrapRequestDTO requestDto) {
        try {
            int result = scrapService.removeScrapProduct(userId, requestDto);
            return ResponseEntity.ok(result);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
