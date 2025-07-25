package com.halggeol.backend.products.unified.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.halggeol.backend.products.unified.dto.UnifiedProductResponseDTO;
import com.halggeol.backend.products.unified.mapper.UnifiedProductMapper;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class UnifiedProductServiceImplTest {

    @Mock // 실제 객체를 대신해주는 테스트용 객체
    private UnifiedProductMapper unifiedProductMapper;

    @InjectMocks // 주입
    private UnifiedProductServiceImpl unifiedProductService;

    @BeforeEach
    void setUp() { // Mock 초기화
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("전체 금융 상품 리스트 조회 성공")
    void getAllProducts() {
        // given : Mock 데이터 설정
        UnifiedProductResponseDTO product1 = UnifiedProductResponseDTO.builder()
            .productId("D1")
            .name("Deposit A")
            .company("A 은행")
            .tag1("12")
            .tag2("36")
            .tag3("")
            .title("3.5")
            .subTitle("3.2")
            .build();

        UnifiedProductResponseDTO product2 = UnifiedProductResponseDTO.builder()
            .productId("S1")
            .name("Savings B")
            .company("B 은행")
            .tag1("12")
            .tag2("36")
            .tag3("")
            .title("3.5")
            .subTitle("3.2")
            .build();

        when(unifiedProductMapper.selectAllUnifiedProducts()).thenReturn(
            Arrays.asList(product1, product2));

        // when : 서비스 호출
        List<UnifiedProductResponseDTO> result = unifiedProductService.getAllProducts();

        // then : 결과 검증
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("D1", result.get(0).getProductId());
        assertEquals("Savings B", result.get(1).getName());

        // mapper 호출 여부 검증
        verify(unifiedProductMapper, times(1)).selectAllUnifiedProducts();
    }
}