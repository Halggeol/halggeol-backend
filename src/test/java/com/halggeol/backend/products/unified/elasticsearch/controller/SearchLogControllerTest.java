package com.halggeol.backend.products.unified.elasticsearch.controller;

import com.halggeol.backend.products.unified.elasticsearch.dto.PopularSearchResponseDTO;
import com.halggeol.backend.products.unified.elasticsearch.dto.RecentSearchResponseDTO;
import com.halggeol.backend.products.unified.elasticsearch.service.SearchLogService;
import com.halggeol.backend.domain.CustomUser;
import com.halggeol.backend.domain.User;
import java.time.Instant;
import org.junit.jupiter.api.BeforeEach;
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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("SearchLogController 단위 테스트")
class SearchLogControllerTest {

    @Mock // 의존성인 SearchLogService를 가짜 객체로 생성
    private SearchLogService searchLogService;

    @InjectMocks // @Mock으로 만든 가짜 객체를 컨트롤러에 주입
    private SearchLogController searchLogController;

    private CustomUser testUser;
    private Integer testUserId;

    @BeforeEach
    void setUp() {
        testUserId = 123;
        User user = User.builder()
            .id(testUserId)
            .email("test@example.com")
            .password("password")
            .build();
        testUser = new CustomUser(user);
    }

    // --- 최근 검색어 조회 테스트 ---

    @Test
    @DisplayName("최근 검색어 조회: 인증된 사용자 요청 시 200 OK와 함께 검색어 목록 반환")
    void getRecentSearches_WithAuthenticatedUser_ShouldReturnOk() {
        // Given
        RecentSearchResponseDTO mockDto = RecentSearchResponseDTO.builder()
            .keyword("테스트 검색어")
            .timestamp(Instant.now())
            .userId(testUserId)
            .build();
        List<RecentSearchResponseDTO> mockSearches = Collections.singletonList(mockDto);
        // searchLogService.getRecentSearches가 호출되면, 위에서 만든 가짜 목록을 반환하도록 설정
        when(searchLogService.getRecentSearches(testUserId)).thenReturn(mockSearches);

        // When
        ResponseEntity<?> response = searchLogController.getRecentSearches(testUser);

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(mockSearches);
        // 서비스 메서드가 정확히 1번, 올바른 userId로 호출되었는지 검증
        verify(searchLogService, times(1)).getRecentSearches(testUserId);
    }

    @Test
    @DisplayName("최근 검색어 조회: 미인증 사용자(null) 요청 시 401 Unauthorized 반환")
    void getRecentSearches_WithNullUser_ShouldReturnUnauthorized() {
        // When
        ResponseEntity<?> response = searchLogController.getRecentSearches(null);

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
        // 서비스가 전혀 호출되지 않았는지 검증
        verifyNoInteractions(searchLogService);
    }

    // --- 최근 검색어 개별 삭제 테스트 ---

    @Test
    @DisplayName("최근 검색어 개별 삭제: 인증된 사용자 요청 시 204 No Content 반환")
    void deleteRecentSearch_WithAuthenticatedUser_ShouldReturnNoContent() {
        // Given
        String keywordToDelete = "삭제할검색어";
        // void를 반환하는 메서드는 doNothing()으로 설정
        doNothing().when(searchLogService).deleteRecentSearch(keywordToDelete, testUserId);

        // When
        ResponseEntity<?> response = searchLogController.deleteRecentSearch(keywordToDelete, testUser);

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        // 서비스 메서드가 정확히 1번, 올바른 인자로 호출되었는지 검증
        verify(searchLogService, times(1)).deleteRecentSearch(keywordToDelete, testUserId);
    }

    @Test
    @DisplayName("최근 검색어 개별 삭제: 미인증 사용자 요청 시 401 Unauthorized 반환")
    void deleteRecentSearch_WithNullUser_ShouldReturnUnauthorized() {
        // When
        ResponseEntity<?> response = searchLogController.deleteRecentSearch("anyKeyword", null);

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
        verifyNoInteractions(searchLogService);
    }

    // --- 최근 검색어 전체 삭제 테스트 ---

    @Test
    @DisplayName("최근 검색어 전체 삭제: 인증된 사용자 요청 시 204 No Content 반환")
    void deleteAllSearches_WithAuthenticatedUser_ShouldReturnNoContent() {
        // Given
        doNothing().when(searchLogService).deleteAllRecentSearches(testUserId);

        // When
        ResponseEntity<?> response = searchLogController.deleteAllSearches(testUser);

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        verify(searchLogService, times(1)).deleteAllRecentSearches(testUserId);
    }

    @Test
    @DisplayName("최근 검색어 전체 삭제: 미인증 사용자 요청 시 401 Unauthorized 반환")
    void deleteAllSearches_WithNullUser_ShouldReturnUnauthorized() {
        // When
        ResponseEntity<?> response = searchLogController.deleteAllSearches(null);

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
        verifyNoInteractions(searchLogService);
    }

    // --- 인기 검색어 조회 테스트 ---

    @Test
    @DisplayName("인기 검색어 조회: 항상 200 OK와 함께 검색어 목록 반환")
    void getPopularSearches_ShouldReturnOk() {
        // Given
        PopularSearchResponseDTO mockDto = PopularSearchResponseDTO.builder()
            .keyword("인기 검색어")
            .count(100)
            .lastSearchTime(Instant.now())
            .build();
        List<PopularSearchResponseDTO> mockSearches = Collections.singletonList(mockDto);
        when(searchLogService.getPopularSearches()).thenReturn(mockSearches);

        // When
        ResponseEntity<?> response = searchLogController.getPopularSearches();

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(mockSearches);
        verify(searchLogService, times(1)).getPopularSearches();
    }
}