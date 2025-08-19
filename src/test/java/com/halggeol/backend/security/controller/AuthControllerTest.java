package com.halggeol.backend.security.controller;

import com.halggeol.backend.domain.CustomUser;
import com.halggeol.backend.domain.User;
import com.halggeol.backend.security.dto.FindEmailDTO;
import com.halggeol.backend.security.dto.ResetPasswordDTO;
import com.halggeol.backend.security.dto.ReverifyPasswordDTO;
import com.halggeol.backend.security.service.AuthService;
import com.halggeol.backend.user.dto.EmailDTO;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@DisplayName("AuthController Mockito 단위 테스트")
class AuthControllerTest {

    @Mock
    private AuthService authService;

    @InjectMocks
    private AuthController authController;

    private CustomUser testUser;

    @BeforeEach
    void setUp() {
        User user = User.builder()
            .id(1)
            .email("test@example.com")
            .name("테스트 사용자")
            .password("password")
            .build();
        testUser = new CustomUser(user);
    }

    @Test
    @DisplayName("extendLogin: 성공 응답 확인")
    void extendLogin_success() {
        /* Given */
        given(authService.extendLogin(testUser.getUsername()))
            .willReturn(Map.of("token", "newToken"));

        /* When */
        ResponseEntity<Map<String, String>> response = authController.extendLogin(testUser);

        /* Then */
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).containsKey("token");
        verify(authService, times(1)).extendLogin(eq(testUser.getUsername()));
    }

    @Test
    @DisplayName("findEmail: 성공 응답 확인")
    void findEmail_success() {
        /* Given */
        FindEmailDTO dto = new FindEmailDTO();
        given(authService.findEmail(any(FindEmailDTO.class)))
            .willReturn(Map.of("email", "test@example.com"));

        /* When */
        ResponseEntity<Map<String, Object>> response = authController.findEmail(dto);

        /* Then */
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        verify(authService, times(1)).findEmail(eq(dto));
    }

    @Test
    @DisplayName("reverifyPassword: 성공 응답 확인")
    void reverifyPassword_success() {
        /* Given */
        ReverifyPasswordDTO dto = new ReverifyPasswordDTO();
        given(authService.reverifyPassword(testUser, dto))
            .willReturn(Map.of("message", "비밀번호 확인 완료"));

        /* When */
        ResponseEntity<Map<String, String>> response = authController.reverifyPassword(testUser, dto);

        /* Then */
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        verify(authService, times(1)).reverifyPassword(eq(testUser), eq(dto));
    }

    @Test
    @DisplayName("resetPasswordWithLogin: 성공 응답 확인")
    void resetPasswordWithLogin_success() {
        /* Given */
        ResetPasswordDTO dto = new ResetPasswordDTO();
        String token = "Bearer token";
        given(authService.resetPasswordWithLogin(testUser, dto, token))
            .willReturn(Map.of("message", "비밀번호 재설정 완료"));

        /* When */
        ResponseEntity<Map<String, String>> response = authController.resetPasswordWithLogin(testUser, dto, token);

        /* Then */
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        verify(authService, times(1)).resetPasswordWithLogin(eq(testUser), eq(dto), eq(token));
    }

    @Test
    @DisplayName("requestResetPassword: 성공 응답 확인")
    void requestResetPassword_success() {
        /* Given */
        EmailDTO emailDTO = new EmailDTO();
        emailDTO.setEmail("test@example.com");

        ResponseEntity<Map<String, Object>> mockResponse = ResponseEntity.ok(Map.of("message", "재설정 메일 발송"));
        given(authService.requestResetPassword(emailDTO)).willReturn(mockResponse);

        /* When */
        ResponseEntity<Map<String, Object>> response = authController.requestResetPassword(emailDTO);

        /* Then */
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        verify(authService, times(1)).requestResetPassword(eq(emailDTO));
    }

    @Test
    @DisplayName("resetPasswordWithoutLogin: 성공 응답 확인")
    void resetPasswordWithoutLogin_success() {
        /* Given */
        ResetPasswordDTO dto = new ResetPasswordDTO();
        String token = "token123";
        given(authService.resetPasswordWithoutLogin(dto, token))
            .willReturn(Map.of("message", "비밀번호 재설정 완료"));

        /* When */
        ResponseEntity<Map<String, String>> response = authController.resetPasswordWithoutLogin(dto, token);

        /* Then */
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        verify(authService, times(1)).resetPasswordWithoutLogin(eq(dto), eq(token));
    }
}
