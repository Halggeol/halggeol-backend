package com.halggeol.backend.products.service;

import com.halggeol.backend.products.dto.ProductFeedbackRequestDTO;
import org.springframework.http.ResponseEntity;

public interface ProductFeedbackService {

    public ResponseEntity<?> createFeedback(ProductFeedbackRequestDTO feedbackDTO);
}
