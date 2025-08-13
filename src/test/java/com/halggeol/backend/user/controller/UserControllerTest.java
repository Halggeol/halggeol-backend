package com.halggeol.backend.user.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.halggeol.backend.security.domain.CustomUser;
import com.halggeol.backend.security.domain.User;
import com.halggeol.backend.user.dto.EmailDTO;
import com.halggeol.backend.user.dto.UpdateCycleRequestDTO;
import com.halggeol.backend.user.dto.UpdateProfileDTO;
import com.halggeol.backend.user.dto.UserJoinDTO;
import com.halggeol.backend.user.dto.UserProfileResponseDTO;
import com.halggeol.backend.user.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@DisplayName("UserController Mockito 단위 테스트")
class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private CustomUser testUser;
    private int testUserId;

    @BeforeEach
    void setUp() {
        testUserId = 1;
        User user = User.builder()
            .id(1)
            .email("test@example.com")
            .name("테스트 사용자")
            .password("password")
            .build();
        testUser = new CustomUser(user);
    }

    @DisplayName("requestJoin: 성공 응답 확인")
    @Test
    void requestJoin_success() {
        /* Given */
        EmailDTO emailDTO = new EmailDTO();
        emailDTO.setEmail("test@example.com");

        // 반환되어야 하는 값
        Map<String, Object> mockResponseBody = new HashMap<>();
        mockResponseBody.put("message", "메일 발송 성공");

        // userService.requestJoin가 반환할 값
        given(userService.requestJoin(any(EmailDTO.class)))
            .willReturn(new ResponseEntity<>(mockResponseBody, HttpStatus.OK));

        /* When */
        // 컨트롤러 메서드를 직접 호출 (HTTP 요청 시뮬레이션 아님)
        ResponseEntity<?> response = userController.requestJoin(emailDTO);

        /* Then */
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        // userService.requestJoin이 올바른 인자로 1번 호출되었는지 검증
        verify(userService, times(1)).requestJoin(eq(emailDTO));
    }

    @Test
    void join_success() {
        /* Given */
        UserJoinDTO userJoinDTO = new UserJoinDTO();
        userJoinDTO.setEmail("test@example.com");
        userJoinDTO.setPassword("password123");

        String token = "";

        Map<String, Object> mockResponseBody = new HashMap<>();
        mockResponseBody.put("message", "회원가입 완료");

        given(userService.join(any(UserJoinDTO.class), anyString()))
            .willReturn(new ResponseEntity<>(mockResponseBody, HttpStatus.CREATED));

        /* When */
        ResponseEntity<?> response = userController.join(userJoinDTO, token);

        /* Then */
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        verify(userService, times(1)).join(any(UserJoinDTO.class), anyString());
    }

    @Test
    void viewProfile_success() {
        /* Given */
        User testUser = new User();
        testUser.setEmail("test@example.com");
        testUser.setPassword("password123");

        CustomUser customUser = new CustomUser(testUser);

        UserProfileResponseDTO profile = new UserProfileResponseDTO();
        profile.setPhone("01012341234");

        Map<String, Object> mockResponseBody = new HashMap<>();
        mockResponseBody.put("message", "회원가입 완료");
        mockResponseBody.put("profile", profile.toMap());

        given(userService.viewProfile(any(CustomUser.class), anyString()))
            .willReturn(Map.of("message", "회원가입 완료",  "profile", profile.toMap()));

        /* When */
        ResponseEntity<?> response = userController.viewProfile(customUser, "");

        /* Then */
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        verify(userService, times(1)).viewProfile(any(CustomUser.class), anyString());
    }

    @Test
    void updateProfile_success() {
        /* Given */
        User testUser = new User();
        testUser.setEmail("test@example.com");
        testUser.setPassword("password123");

        CustomUser customUser = new CustomUser(testUser);

        UpdateProfileDTO updateProfileDTO = new UpdateProfileDTO();
        updateProfileDTO.setPhone("01012341234");

        Map<String, Object> mockResponseBody = new HashMap<>();
        mockResponseBody.put("message", "사용자 정보 수정 완료");

        given(userService.updateProfile(any(CustomUser.class), any(UpdateProfileDTO.class)))
            .willReturn(Map.of("message", "회원가입 완료"));

        /* When */
        ResponseEntity<?> response = userController.updateProfile(customUser, updateProfileDTO);

        /* Then */
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        verify(userService, times(1)).updateProfile(any(CustomUser.class), any(UpdateProfileDTO.class));
    }

    @Test
    void markAsDeleted_success() {
        /* Given */
        User testUser = new User();
        testUser.setEmail("test@example.com");
        testUser.setPassword("password123");

        CustomUser customUser = new CustomUser(testUser);

        Map<String, Object> mockResponseBody = new HashMap<>();
        mockResponseBody.put("message", "회원탈퇴 완료");

        given(userService.markAsDeleted(any(CustomUser.class), anyString()))
            .willReturn(Map.of("message", "회원탈퇴 완료"));

        /* When */
        ResponseEntity<?> response = userController.markAsDeleted(customUser, "");

        /* Then */
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        verify(userService, times(1)).markAsDeleted(any(CustomUser.class), anyString());
    }

    @Test
    void updateInsightCycle_success() {
        /* Given */
        User testUser = new User();
        testUser.setEmail("test@example.com");
        testUser.setPassword("password123");

        CustomUser customUser = new CustomUser(testUser);

        UpdateCycleRequestDTO updateCycleRequestDTO = new UpdateCycleRequestDTO();
        updateCycleRequestDTO.setCycle("WEEKLY_1");

        Map<String, Object> mockResponseBody = new HashMap<>();
        mockResponseBody.put("message", "인사이트 주기 변경 완료");

        given(
            userService.updateInsightCycle(any(CustomUser.class), any(UpdateCycleRequestDTO.class)))
            .willReturn(Map.of("message", "인사이트 주기 변경 완료"));

        /* When */
        ResponseEntity<?> response = userController.updateInsightCycle(customUser,
            updateCycleRequestDTO);

        /* Then */
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        verify(userService, times(1)).updateInsightCycle(any(CustomUser.class),
            any(UpdateCycleRequestDTO.class));
    }
}
