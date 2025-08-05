package com.halggeol.backend.products.service;

import com.halggeol.backend.products.dto.ProductFeedbackRequestDTO;
import com.halggeol.backend.products.mapper.ProductFeedbackMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductFeedbackServiceImpl implements ProductFeedbackService{


    private final ProductFeedbackMapper mapper;

    @Override
    public ResponseEntity<?> createFeedback(ProductFeedbackRequestDTO feedbackDTO) {
        // 피드백 생성 로직을 여기에 구현
        // 예: mapper.map(feedbackDTO);
        // 요청 바디에서 피드백 정보를 받아와야 합니다. ProductFeedbackRequestDTO를 사용할 예정
        // 피드백 생성 후 응답 반환
        mapper.createFeedback(feedbackDTO.getPid(), feedbackDTO.getUid().toString(), feedbackDTO.getReason(), feedbackDTO.getAnlzDate());
        String feedbackReason = feedbackDTO.getReason();
        String userId = feedbackDTO.getUid().toString();
        // 피드백 내용에 따라 유저 벡터 업데이트 로직을 추가해야 합니다.
        if (feedbackReason == null || feedbackReason.isEmpty()) {
            return ResponseEntity.badRequest().body("피드백 이유가 비어있습니다.");
        }
        return ResponseEntity.ok("피드백이 성공적으로 생성되었습니다.");
    }

    private ResponseEntity<?> updateFeedback(String feedbackReason, String userId) {
        // 피드백 업데이트 로직을 여기에 구현
        // 예: mapper.update(feedbackDTO);
        // 요청 바디에서 피드백 정보를 받아와야 합니다. ProductFeedbackRequestDTO를 사용할 예정
        // 피드백 업데이트 후 응답 반환
        return ResponseEntity.ok("피드백이 성공적으로 업데이트되었습니다.");
    }
}
