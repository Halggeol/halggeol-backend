package com.halggeol.backend.products.mapper;

import com.halggeol.backend.products.dto.DepositDetailResponseDTO;
import com.halggeol.backend.products.dto.ForexDetailResponseDTO;
import com.halggeol.backend.products.dto.FundDetailResponseDTO;
import com.halggeol.backend.products.dto.PensionDetailResponseDTO;
import com.halggeol.backend.products.dto.SavingsDetailResponseDTO;
import com.halggeol.backend.products.dto.UserSpecificDataResponseDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mybatis.spring.SqlSessionTemplate;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class ProductDetailMapperTest {

    @Mock
    private SqlSessionTemplate sqlSessionTemplate;

    @InjectMocks
    private ProductDetailMapper productDetailMapper = new ProductDetailMapper() {
        // 인터페이스의 모든 메서드를 익명 클래스로 구현
        @Override
        public DepositDetailResponseDTO selectBaseDepositDetailById(String depositId) {
            return sqlSessionTemplate.selectOne("com.halggeol.backend.products.mapper.ProductDetailMapper.selectBaseDepositDetailById", depositId);
        }

        @Override
        public UserSpecificDataResponseDTO selectUserSpecificDataForDeposit(String userId, String productId) {
            return sqlSessionTemplate.selectOne("com.halggeol.backend.products.mapper.ProductDetailMapper.selectUserSpecificDataForDeposit", new Object[]{userId, productId});
        }

        @Override
        public SavingsDetailResponseDTO selectBaseSavingsDetailById(String savingsId) {
            return sqlSessionTemplate.selectOne("com.halggeol.backend.products.mapper.ProductDetailMapper.selectBaseSavingsDetailById", savingsId);
        }

        @Override
        public UserSpecificDataResponseDTO selectUserSpecificDataForSavings(String userId, String productId) {
            return sqlSessionTemplate.selectOne("com.halggeol.backend.products.mapper.ProductDetailMapper.selectUserSpecificDataForSavings", new Object[]{userId, productId});
        }

        @Override
        public FundDetailResponseDTO selectBaseFundDetailById(String fundId) {
            return sqlSessionTemplate.selectOne("com.halggeol.backend.products.mapper.ProductDetailMapper.selectBaseFundDetailById", fundId);
        }

        @Override
        public UserSpecificDataResponseDTO selectUserSpecificDataForFund(String userId, String productId) {
            return sqlSessionTemplate.selectOne("com.halggeol.backend.products.mapper.ProductDetailMapper.selectUserSpecificDataForFund", new Object[]{userId, productId});
        }

        @Override
        public ForexDetailResponseDTO selectBaseForexDetailById(String forexId) {
            return sqlSessionTemplate.selectOne("com.halggeol.backend.products.mapper.ProductDetailMapper.selectBaseForexDetailById", forexId);
        }

        @Override
        public UserSpecificDataResponseDTO selectUserSpecificDataForForex(String userId, String productId) {
            return sqlSessionTemplate.selectOne("com.halggeol.backend.products.mapper.ProductDetailMapper.selectUserSpecificDataForForex", new Object[]{userId, productId});
        }

        @Override
        public PensionDetailResponseDTO selectBasePensionDetailById(String pensionId) {
            return sqlSessionTemplate.selectOne("com.halggeol.backend.products.mapper.ProductDetailMapper.selectBasePensionDetailById", pensionId);
        }

        @Override
        public UserSpecificDataResponseDTO selectUserSpecificDataForPension(String userId, String productId) {
            return sqlSessionTemplate.selectOne("com.halggeol.backend.products.mapper.ProductDetailMapper.selectUserSpecificDataForPension", new Object[]{userId, productId});
        }

        @Override
        public void incrementDepositViewCount(String productId) {
            sqlSessionTemplate.update("com.halggeol.backend.products.mapper.ProductDetailMapper.incrementDepositViewCount", productId);
        }

        @Override
        public void incrementSavingsViewCount(String productId) {
            sqlSessionTemplate.update("com.halggeol.backend.products.mapper.ProductDetailMapper.incrementSavingsViewCount", productId);
        }

        @Override
        public void incrementFundViewCount(String productId) {
            sqlSessionTemplate.update("com.halggeol.backend.products.mapper.ProductDetailMapper.incrementFundViewCount", productId);
        }

        @Override
        public void incrementForexViewCount(String productId) {
            sqlSessionTemplate.update("com.halggeol.backend.products.mapper.ProductDetailMapper.incrementForexViewCount", productId);
        }

        @Override
        public void incrementPensionViewCount(String productId) {
            sqlSessionTemplate.update("com.halggeol.backend.products.mapper.ProductDetailMapper.incrementPensionViewCount", productId);
        }

        @Override
        public void updateProductStatus(Integer userId, String productId, String productStatus) {
            sqlSessionTemplate.update("com.halggeol.backend.products.mapper.ProductDetailMapper.updateProductStatus", new Object[]{userId, productId, productStatus});
        }

        @Override
        public String selectProductStatus(Integer userId, String productId) {
            return sqlSessionTemplate.selectOne("com.halggeol.backend.products.mapper.ProductDetailMapper.selectProductStatus", new Object[]{userId, productId});
        }
    };

    private final String TEST_USER_ID = "1";
    private final Integer TEST_USER_ID_INT = 1;

    @Test
    @DisplayName("정기예금 상세 조회 시 DTO를 반환한다.")
    void selectBaseDepositDetailById_returnsDto() {
        // given
        DepositDetailResponseDTO expectedDto = new DepositDetailResponseDTO();
        when(sqlSessionTemplate.selectOne(anyString(), any(Object.class))).thenReturn(expectedDto);

        // when
        DepositDetailResponseDTO result = productDetailMapper.selectBaseDepositDetailById("D001");

        // then
        assertThat(result).isSameAs(expectedDto);
        verify(sqlSessionTemplate).selectOne(eq("com.halggeol.backend.products.mapper.ProductDetailMapper.selectBaseDepositDetailById"), eq("D001"));
    }

    @Test
    @DisplayName("적금 상세 조회 시 DTO를 반환한다.")
    void selectBaseSavingsDetailById_returnsDto() {
        // given
        SavingsDetailResponseDTO expectedDto = new SavingsDetailResponseDTO();
        when(sqlSessionTemplate.selectOne(anyString(), any(Object.class))).thenReturn(expectedDto);

        // when
        SavingsDetailResponseDTO result = productDetailMapper.selectBaseSavingsDetailById("S001");

        // then
        assertThat(result).isSameAs(expectedDto);
        verify(sqlSessionTemplate).selectOne(eq("com.halggeol.backend.products.mapper.ProductDetailMapper.selectBaseSavingsDetailById"), eq("S001"));
    }

    @Test
    @DisplayName("사용자 특정 데이터 조회 시 DTO를 반환한다.")
    void selectUserSpecificDataForDeposit_returnsDto() {
        // given
        UserSpecificDataResponseDTO expectedDto = new UserSpecificDataResponseDTO();
        when(sqlSessionTemplate.selectOne(anyString(), any(Object.class))).thenReturn(expectedDto);

        // when
        UserSpecificDataResponseDTO result = productDetailMapper.selectUserSpecificDataForDeposit(TEST_USER_ID, "D001");

        // then
        assertThat(result).isSameAs(expectedDto);
        verify(sqlSessionTemplate).selectOne(eq("com.halggeol.backend.products.mapper.ProductDetailMapper.selectUserSpecificDataForDeposit"), any(Object.class));
    }

    @Test
    @DisplayName("조회수 증가 메서드 호출 시 update가 실행된다.")
    void incrementDepositViewCount_callsUpdate() {
        // given
        when(sqlSessionTemplate.update(anyString(), any(Object.class))).thenReturn(1);

        // when
        productDetailMapper.incrementDepositViewCount("D001");

        // then
        verify(sqlSessionTemplate).update(eq("com.halggeol.backend.products.mapper.ProductDetailMapper.incrementDepositViewCount"), eq("D001"));
    }

    @Test
    @DisplayName("상품 상태 업데이트 시 update가 실행된다.")
    void updateProductStatus_callsUpdate() {
        // given
        when(sqlSessionTemplate.update(anyString(), any(Object.class))).thenReturn(1);

        // when
        productDetailMapper.updateProductStatus(TEST_USER_ID_INT, "D001", "DONE");

        // then
        verify(sqlSessionTemplate).update(eq("com.halggeol.backend.products.mapper.ProductDetailMapper.updateProductStatus"), any(Object.class));
    }

    @Test
    @DisplayName("상품 상태 조회 시 문자열을 반환한다.")
    void selectProductStatus_returnsString() {
        // given
        String expectedStatus = "DONE";
        when(sqlSessionTemplate.selectOne(anyString(), any(Object.class))).thenReturn(expectedStatus);

        // when
        String result = productDetailMapper.selectProductStatus(TEST_USER_ID_INT, "D001");

        // then
        assertThat(result).isEqualTo(expectedStatus);
        verify(sqlSessionTemplate).selectOne(eq("com.halggeol.backend.products.mapper.ProductDetailMapper.selectProductStatus"), any(Object.class));
    }
}
