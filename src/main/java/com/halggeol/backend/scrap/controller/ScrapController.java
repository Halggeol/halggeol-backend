package com.halggeol.backend.scrap.controller;

import com.halggeol.backend.scrap.dto.ScrapRequestDTO;
import com.halggeol.backend.scrap.service.ScrapService;
import com.halggeol.backend.domain.CustomUser;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
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
        @AuthenticationPrincipal CustomUser user,
        @RequestBody ScrapRequestDTO requestDto) {
        try {
            scrapService.addScrapProduct(user, requestDto);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping
    public ResponseEntity<?> removeScrapProduct(
        @AuthenticationPrincipal CustomUser user,
        @RequestBody ScrapRequestDTO requestDto) {
        try {
            scrapService.removeScrapProduct(user, requestDto);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<?> getScrapedProducts(
        @AuthenticationPrincipal CustomUser user,
        @RequestParam(required = false) List<String> types,
        @RequestParam(required = false) String sort
    ){
        return scrapService.getScrappedProducts(user, types, sort);
    }

}
