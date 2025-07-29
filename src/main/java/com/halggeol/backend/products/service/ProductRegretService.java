package com.halggeol.backend.products.service;

import org.springframework.http.ResponseEntity;

public interface ProductRegretService {
    ResponseEntity<?> getRegretRecommendTop5();
}
