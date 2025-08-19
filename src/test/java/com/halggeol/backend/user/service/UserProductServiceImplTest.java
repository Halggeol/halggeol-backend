package com.halggeol.backend.user.service;

import com.halggeol.backend.domain.CustomUser;
import com.halggeol.backend.domain.User;
import com.halggeol.backend.user.dto.UserProductResponseDTO;
import com.halggeol.backend.user.mapper.UserProductMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserProductServiceImplTest {

    @Mock
    private UserProductMapper userProductMapper;

    @InjectMocks
    private UserProductServiceImpl userProductService;

    private CustomUser mockUser;
    private final Integer TEST_USER_ID = 1;

    @BeforeEach
    void setUp() {
        mockUser = mock(CustomUser.class);
        User coreUser = mock(User.class);
        when(mockUser.getUser()).thenReturn(coreUser);
        when(coreUser.getId()).thenReturn(TEST_USER_ID);
    }

    @Test
    @DisplayName("사용자 ID로 상품 목록 조회 시 Mapper를 호출하고 DTO 리스트를 반환한다.")
    void getUserProductsByUserId_returnsProductList() {
        // given
        List<UserProductResponseDTO> expectedList = Collections.singletonList(
            UserProductResponseDTO.builder().productId("D001").build()
        );
        when(userProductMapper.getUserProductsByUserId(anyInt())).thenReturn(expectedList);

        // when
        List<UserProductResponseDTO> result = userProductService.getUserProductsByUserId(mockUser);

        // then
        assertThat(result).isSameAs(expectedList);

        // 메서드가 올바른 인자(사용자 ID)로 한 번 호출되었는지 검증
        verify(userProductMapper).getUserProductsByUserId(TEST_USER_ID);
    }

    @Test
    @DisplayName("상품 목록이 비어있을 경우, 비어있는 리스트를 반환한다.")
    void getUserProductsByUserId_returnsEmptyList() {
        // given
        List<UserProductResponseDTO> expectedList = Collections.emptyList();
        when(userProductMapper.getUserProductsByUserId(anyInt())).thenReturn(expectedList);

        // when
        List<UserProductResponseDTO> result = userProductService.getUserProductsByUserId(mockUser);

        // then
        assertThat(result).isSameAs(expectedList);
        verify(userProductMapper).getUserProductsByUserId(TEST_USER_ID);
    }
}
