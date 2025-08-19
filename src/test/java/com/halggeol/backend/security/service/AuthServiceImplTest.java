package com.halggeol.backend.security.service;

import com.halggeol.backend.domain.CustomUser;
import com.halggeol.backend.security.dto.FindEmailDTO;
import com.halggeol.backend.security.dto.ResetPasswordDTO;
import com.halggeol.backend.security.dto.ReverifyPasswordDTO;
import com.halggeol.backend.security.mail.domain.MailType;
import com.halggeol.backend.security.mail.service.MailService;
import com.halggeol.backend.security.util.JwtManager;
import com.halggeol.backend.user.dto.EmailDTO;
import com.halggeol.backend.user.mapper.UserMapper;
import com.halggeol.backend.user.service.UserService;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.web.server.ResponseStatusException;
import com.halggeol.backend.domain.User;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("AuthServiceImplTest Mockito 단위 테스트")
class AuthServiceImplTest {

    @Mock
    private JwtManager jwtManager;
    @Mock
    private UserMapper userMapper;
    @Mock
    private Argon2PasswordEncoder passwordEncoder;
    @Mock
    private UserService userService;
    @Mock
    private MailService mailService;

    @InjectMocks
    private AuthServiceImpl authService;

    @Test
    @DisplayName("extendLogin - 정상 동작")
    void extendLogin_success() {
        /* Given */
        when(jwtManager.generateAccessToken(anyString())).thenReturn("newToken");

        /* When */
        Map<String, String> result = authService.extendLogin(anyString());

        /* Then */
        assertThat(result).containsEntry("message", "로그인 시간이 연장되었습니다.")
            .containsEntry("accessToken", "newToken");
    }

    @Test
    @DisplayName("findEmail - 일치하는 이메일 없을 경우 메시지 반환")
    void findEmail_noMatch() {
        /* Given */
        FindEmailDTO dto = new FindEmailDTO();
        dto.setName("홍길동");
        dto.setPhone("01012345678");

        when(userMapper.findEmailByNameAndPhone(anyString(), anyString())).thenReturn(List.of());

        /* When */
        Map<String, Object> result = authService.findEmail(dto);

        /* Then */
        assertThat(result).containsEntry("message", "일치하는 사용자 정보가 없습니다.");
    }

    @Test
    @DisplayName("findEmail - 이메일 마스킹 후 반환")
    void findEmail_success() {
        /* Given */
        FindEmailDTO dto = new FindEmailDTO();
        dto.setName("홍길동");
        dto.setPhone("01012345678");

        when(userMapper.findEmailByNameAndPhone(anyString(), anyString()))
            .thenReturn(List.of("test@example.com"));

        /* When */
        Map<String, Object> result = authService.findEmail(dto);

        /* Then */
        assertThat(result.get("message")).isEqualTo("아이디 찾기에 성공했습니다.");
        List<String> masked = (List<String>) result.get("email");
        assertThat(masked.get(0)).startsWith("t***@");
    }

    @Test
    @DisplayName("reverifyPassword - 비밀번호 불일치 시 예외 발생")
    void reverifyPassword_mismatch_throwsException() {
        /* Given */
        CustomUser customUser = mock(CustomUser.class);
        when(customUser.getPassword()).thenReturn("encodedPassword");

        ReverifyPasswordDTO dto = new ReverifyPasswordDTO();
        dto.setConfirmPassword("wrong");

        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(false);

        /* When & Then */
        assertThatThrownBy(() -> authService.reverifyPassword(customUser, dto))
            .isInstanceOf(ResponseStatusException.class)
            .hasMessageContaining("비밀번호가 일치하지 않습니다.");
    }

    @Test
    @DisplayName("resetPasswordWithLogin - 재확인되지 않은 경우 예외 발생")
    void resetPasswordWithLogin_notReverified() {
        /* Given */
        CustomUser customUser = mock(CustomUser.class);
        when(jwtManager.parseBearerToken(anyString())).thenReturn("token");
        when(jwtManager.isReverified("token")).thenReturn(false);

        ResetPasswordDTO dto = mock(ResetPasswordDTO.class);

        /* When & Then */
        assertThatThrownBy(() -> authService.resetPasswordWithLogin(customUser, dto, "bearerToken"))
            .isInstanceOf(ResponseStatusException.class)
            .hasMessageContaining("비밀번호가 재확인되지 않았습니다.");
    }

    @Test
    @DisplayName("requestResetPassword - 존재하지 않는 사용자")
    void requestResetPassword_userNotFound() {
        /* Given */
        EmailDTO dto = new EmailDTO();
        dto.setEmail("test@example.com");

        lenient().when(userService.findByEmail(anyString())).thenReturn(null);

        /* When */
        ResponseEntity<Map<String, Object>> response = authService.requestResetPassword(dto);

        /* Then */
        assertThat(response.getBody()).containsEntry("success", false);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    @DisplayName("requestResetPassword - 정상 이메일 전송")
    void requestResetPassword_success() {
        /* Given */
        EmailDTO dto = new EmailDTO();
        dto.setEmail("test@example.com");

        when(userService.findByEmail("test@example.com")).thenReturn(new User());
        when(jwtManager.generateVerifyToken("test@example.com")).thenReturn("token");

        /* When */
        ResponseEntity<Map<String, Object>> response = authService.requestResetPassword(dto);

        /* Then */
        assertThat(response.getBody()).containsEntry("success", true);
        verify(mailService).sendMail(argThat(mail ->
            mail.getMailType() == MailType.PASSWORD_RESET &&
                mail.getToken().equals("token")
        ));
    }

    @Test
    @DisplayName("resetPasswordWithoutLogin - 토큰 유효하지 않을 경우 예외 발생")
    void resetPasswordWithoutLogin_invalidToken() {
        /* Given */
        ResetPasswordDTO dto = mock(ResetPasswordDTO.class);
        when(jwtManager.validateToken("token")).thenReturn(false);

        /* When & Then */
        assertThatThrownBy(() -> authService.resetPasswordWithoutLogin(dto, "token"))
            .isInstanceOf(ResponseStatusException.class)
            .hasMessageContaining("토큰이 유효하지 않습니다.");
    }
}
