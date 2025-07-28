package com.halggeol.backend.products.unified.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.halggeol.backend.products.unified.dto.UnifiedProductResponseDTO;
import com.halggeol.backend.products.unified.mapper.UnifiedProductMapper;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class UnifiedProductServiceImplTest {

    @Mock
    private UnifiedProductMapper unifiedProductMapper;

    @InjectMocks
    private UnifiedProductServiceImpl unifiedProductService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("전체 금융 상품 리스트 조회 성공")
    void getAllProducts() {
        // given
        String sort = "rateDesc";
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
            .viewCnt(123)
            .scrapCnt(45)
            .build();

        List<UnifiedProductResponseDTO> mockResult = List.of(mockProduct);
        when(unifiedProductMapper.selectFilteredProducts(sort, type, fSector, saveTerm, minAmount))
            .thenReturn(mockResult);

        // when
        List<UnifiedProductResponseDTO> result = unifiedProductService
            .getFilteredProducts(sort, type, fSector, saveTerm, minAmount);

        // then
        assertThat(result).isNotNull();
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getProductId()).isEqualTo("D1");
        verify(unifiedProductMapper, times(1)).selectFilteredProducts(sort, type, fSector, saveTerm, minAmount);
    }

    @Test
    @DisplayName("정렬 없이 전체 금융 상품 조회")
    void getProductsWithoutSort() {
        // given
        String sort = null;
        String expectedSort = "popularDesc";
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
            .viewCnt(123)
            .scrapCnt(45)
            .build();

        List<UnifiedProductResponseDTO> mockResult = List.of(mockProduct);
        when(unifiedProductMapper.selectFilteredProducts(expectedSort, type, fSector, saveTerm, minAmount))
            .thenReturn(mockResult);

        // when
        List<UnifiedProductResponseDTO> result = unifiedProductService
            .getFilteredProducts(sort, type, fSector, saveTerm, minAmount);

        // then
        assertThat(result).isNotNull();
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getName()).isEqualTo("Deposit A");
    }
}
