package com.halggeol.backend.user.controller;

import com.halggeol.backend.domain.CustomUser;
import com.halggeol.backend.domain.User;
import com.halggeol.backend.user.dto.UserProductResponseDTO;
import com.halggeol.backend.user.service.UserProductService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserProductControllerTest {

    @Mock
    private UserProductService userProductService;

    @InjectMocks
    private UserProductController userProductController;

    private final Integer TEST_USER_ID = 1;

    @Test
    @DisplayName("사용자 ID로 상품 목록 조회 시 HTTP 200과 상품 리스트를 반환한다.")
    void getUserProductsByUserId_returnsProductList() {
        // given
        CustomUser mockUser = mock(CustomUser.class);
        User coreUser = mock(User.class);
        lenient().when(mockUser.getUser()).thenReturn(coreUser);
        lenient().when(coreUser.getId()).thenReturn(TEST_USER_ID);

        List<UserProductResponseDTO> expectedList = Collections.singletonList(UserProductResponseDTO.builder()
            .productId("D001")
            .name("Test Deposit Product")
            .company("Test Company")
            .isScraped(true)
            .build());

        when(userProductService.getUserProductsByUserId(any(CustomUser.class))).thenReturn(expectedList);

        // when
        ResponseEntity<List<UserProductResponseDTO>> responseEntity = userProductController.getUserProductsByUserId(mockUser);

        // then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isNotNull();
        assertThat(responseEntity.getBody()).hasSize(1);
        assertThat(responseEntity.getBody().get(0).getProductId()).isEqualTo("D001");
    }

    @Test
    @DisplayName("상품 목록이 비어있을 경우, 비어있는 리스트와 HTTP 200을 반환한다.")
    void getUserProductsByUserId_returnsEmptyList() {
        // given
        CustomUser mockUser = mock(CustomUser.class);
        User coreUser = mock(User.class);
        lenient().when(mockUser.getUser()).thenReturn(coreUser);
        lenient().when(coreUser.getId()).thenReturn(TEST_USER_ID);

        List<UserProductResponseDTO> expectedList = Collections.emptyList();
        when(userProductService.getUserProductsByUserId(any(CustomUser.class))).thenReturn(expectedList);

        // when
        ResponseEntity<List<UserProductResponseDTO>> responseEntity = userProductController.getUserProductsByUserId(mockUser);

        // then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isNotNull();
        assertThat(responseEntity.getBody()).isEmpty();
    }
}
