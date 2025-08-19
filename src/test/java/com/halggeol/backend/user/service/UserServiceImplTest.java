package com.halggeol.backend.user.service;

import com.halggeol.backend.domain.CustomUser;
import com.halggeol.backend.domain.User;
import com.halggeol.backend.security.mail.dto.MailDTO;
import com.halggeol.backend.security.mail.service.MailService;
import com.halggeol.backend.security.util.JwtManager;
import com.halggeol.backend.user.dto.EmailDTO;
import com.halggeol.backend.user.dto.UpdateCycleRequestDTO;
import com.halggeol.backend.user.dto.UpdateProfileDTO;
import com.halggeol.backend.user.dto.UserJoinDTO;
import com.halggeol.backend.user.mapper.UserMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("UserService Mockito 테스트")
class UserServiceImplTest {

    @Mock
    private UserMapper userMapper;
    @Mock
    private MailService mailService;
    @Mock
    private JwtManager jwtManager;
    @Mock
    private Argon2PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    @DisplayName("emailExists: 이메일이 null이면 BAD_REQUEST 예외 발생")
    @Test
    void emailExists_nullEmail_throwsException() {
        assertThatThrownBy(() -> userService.emailExists(null))
            .isInstanceOf(ResponseStatusException.class)
            .hasMessageContaining("이메일이 입력되지 않았습니다.");
    }

    @DisplayName("emailExists: 존재하지 않는 이메일이면 NOT_FOUND 예외 발생")
    @Test
    void emailExists_notFound_throwsException() {
        // mockito stub 설정
        when(userMapper.findByEmail("test@example.com")).thenReturn(null);

        assertThatThrownBy(() -> userService.emailExists("test@example.com"))
            .isInstanceOf(ResponseStatusException.class)
            .hasMessageContaining("존재하지 않는 사용자입니다.");
    }

    @DisplayName("requestJoin: 이미 존재하는 사용자일 경우 CONFLICT 응답")
    @Test
    void requestJoin_emailExists_returnsConflict() {
        /* Given */
        EmailDTO email = new EmailDTO();
        email.setEmail("test@example.com");

        when(userMapper.findByEmail("test@example.com")).thenReturn(new User());

        /* When */
        ResponseEntity<Map<String, Object>> response = userService.requestJoin(email);

        /* Then */
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);
        assertThat(response.getBody()).containsEntry("success", false);
    }

    @DisplayName("requestJoin: 새로운 사용자면 메일 전송 후 OK 응답")
    @Test
    void requestJoin_newUser_sendsMailAndReturnsOk() {
        /* Given */
        EmailDTO email = new EmailDTO();
        email.setEmail("test@example.com");

        when(userMapper.findByEmail("test@example.com")).thenReturn(null);
        when(jwtManager.generateVerifyToken("test@example.com")).thenReturn("token");

        /* When */
        ResponseEntity<Map<String, Object>> response = userService.requestJoin(email);

        /* Then */
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).containsEntry("success", true);
        verify(mailService, times(1)).sendMail(any(MailDTO.class));
    }

    @DisplayName("join: 토큰이 유효하지 않으면 UNAUTHORIZED 반환")
    @Test
    void join_invalidToken_returnsUnauthorized() {
        /* Given */
        UserJoinDTO dto = mock(UserJoinDTO.class);
        when(jwtManager.validateToken("token")).thenReturn(false);

        /* When */
        ResponseEntity<Map<String, Object>> response = userService.join(dto, "token");

        /* Then */
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    }

    @DisplayName("updateProfile: 사용자 정보 업데이트 성공")
    @Test
    void updateProfile_success() {
        /* Given */
        CustomUser customUser = mock(CustomUser.class);
        User innerUser = new User();
        innerUser.setId(1);
        when(customUser.getUser()).thenReturn(innerUser);

        UpdateProfileDTO dto = new UpdateProfileDTO();
        dto.setPhone("01012345678");

        /* When */
        Map<String, String> result = userService.updateProfile(customUser, dto);

        /* Then */
        assertThat(result).containsEntry("message", "사용자 정보 수정이 완료되었습니다.");
        verify(userMapper, times(1)).updateProfileById(1, "01012345678");
    }

    @DisplayName("updateInsightCycle: 주기 변경 성공")
    @Test
    void updateInsightCycle_success() {
        /* Given */
        CustomUser customUser = mock(CustomUser.class);
        User innerUser = new User();
        innerUser.setId(1);
        when(customUser.getUser()).thenReturn(innerUser);

        UpdateCycleRequestDTO dto = mock(UpdateCycleRequestDTO.class);
        when(dto.getCycle()).thenReturn("WEEKLY");

        /* When */
        Map<String, String> result = userService.updateInsightCycle(customUser, dto);

        /* Then */
        assertThat(result).containsEntry("message", "인사이트 주기 변경이 완료되었습니다.");
        verify(userMapper, times(1)).updateInsightCycleById(1, "WEEKLY");
        verify(dto, times(1)).validateCycleType();
    }
}
