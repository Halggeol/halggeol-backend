package com.halggeol.backend.products.unified.service;

import static org.assertj.core.api.Assertions.assertThat;
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

        String type = "예금";
        Integer fSector = 1;
        Integer saveTerm = 12;
        String minAmount = "1000000";

        UnifiedProductResponseDTO mockProduct = UnifiedProductResponseDTO.builder()
            .productId("D1")
            .name("Deposit A")
            .company("A 은행")
            .tag1("12")
            .tag2("36")
            .tag3("")
            .title("3.5")
            .subTitle("3.2")
            .type(type)
            .fSector(fSector)
            .saveTerm(saveTerm)
            .minAmount(minAmount)
            .build();

        List<UnifiedProductResponseDTO> mockResult = List.of(mockProduct);

        when(unifiedProductMapper.selectFilteredProducts(type, fSector, saveTerm, minAmount))
            .thenReturn(mockResult);

        // when : 서비스 호출
        List<UnifiedProductResponseDTO> result = unifiedProductService
            .getFilteredProducts(type, fSector, saveTerm, minAmount);

        // then : 결과 검증
        assertThat(result).isNotNull();
        assertThat(result).hasSize(mockResult.size());
        assertThat(result.get(0).getProductId()).isEqualTo("D1");

        // mapper 호출 여부 검증
        verify(unifiedProductMapper, times(1)).selectFilteredProducts(type, fSector, saveTerm, minAmount);
    }
}