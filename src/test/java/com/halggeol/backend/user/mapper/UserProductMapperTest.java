package com.halggeol.backend.user.mapper;

import com.halggeol.backend.user.dto.UserProductResponseDTO;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserProductMapperTest {

    @Mock
    private SqlSessionTemplate sqlSessionTemplate;

    @InjectMocks
    private UserProductMapper userProductMapper = new UserProductMapper() {
        @Override
        public List<UserProductResponseDTO> getUserProductsByUserId(int userId) {
            // SqlSessionTemplate의 selectList를 호출하도록 모의
            return sqlSessionTemplate.selectList("com.halggeol.backend.user.mapper.UserProductMapper.getUserProductsByUserId", userId);
        }
    };

    private final int TEST_USER_ID = 1;

    @Test
    @DisplayName("사용자 ID로 상품 목록 조회 시 DTO 리스트를 반환한다.")
    void getUserProductsByUserId_returnsProductList() {
        // given
        // SqlSessionTemplate이 반환할 가짜 DTO 리스트를 정의합니다.
        List<UserProductResponseDTO> expectedList = Collections.singletonList(
            UserProductResponseDTO.builder().productId("D001").build()
        );
        when(sqlSessionTemplate.selectList(anyString(), any(Object.class))).thenReturn((List<Object>) (List<?>) expectedList);

        // when
        List<UserProductResponseDTO> result = userProductMapper.getUserProductsByUserId(TEST_USER_ID);

        // then
        // 1. 반환된 리스트가 예상 리스트와 동일한지 확인
        assertThat(result).isSameAs(expectedList);
        // 2. SqlSessionTemplate의 selectList 메서드가 올바른 인자(쿼리 ID, 사용자 ID)로 호출되었는지 검증
        verify(sqlSessionTemplate).selectList(eq("com.halggeol.backend.user.mapper.UserProductMapper.getUserProductsByUserId"), eq(TEST_USER_ID));
    }

    @Test
    @DisplayName("상품 목록이 비어있을 경우, 비어있는 리스트를 반환한다.")
    void getUserProductsByUserId_returnsEmptyList() {
        // given
        List<UserProductResponseDTO> expectedList = Collections.emptyList();
        when(sqlSessionTemplate.selectList(anyString(), any(Object.class))).thenReturn((List<Object>) (List<?>) expectedList);

        // when
        List<UserProductResponseDTO> result = userProductMapper.getUserProductsByUserId(TEST_USER_ID);

        // then
        assertThat(result).isSameAs(expectedList);
        verify(sqlSessionTemplate).selectList(eq("com.halggeol.backend.user.mapper.UserProductMapper.getUserProductsByUserId"), eq(TEST_USER_ID));
    }
}
