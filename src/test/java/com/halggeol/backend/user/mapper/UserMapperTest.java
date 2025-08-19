package com.halggeol.backend.user.mapper;

import com.halggeol.backend.domain.User;
import com.halggeol.backend.security.mail.service.MailService;
import com.halggeol.backend.security.util.JwtManager;
import com.halggeol.backend.user.dto.UserJoinDTO;
import com.halggeol.backend.user.dto.UserProfileResponseDTO;
import com.halggeol.backend.user.service.UserServiceImpl;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@DisplayName("UserMapper Mockito 단위 테스트")
class UserMapperTest {

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

    @DisplayName("insert 호출 여부 확인")
    @Test
    void insert() {
        // 서비스가 데이터 접근 계층(매퍼)을 제대로 호출하는지 보장하기 위해 호출 여부 확인

        /* Given */
        UserJoinDTO userJoinDTO = new UserJoinDTO();
        userJoinDTO.setEmail("email");
        userJoinDTO.setPassword("password");
        userJoinDTO.setConfirmPassword("password");
        userJoinDTO.setPhone("01012341234");
        userJoinDTO.setBirth("1990-01-01");

        String token = "valid-token";

        given(jwtManager.validateToken(token)).willReturn(true);
        given(jwtManager.getEmail(token)).willReturn("test@example.com");
        given(passwordEncoder.encode(anyString())).willReturn("encodedPassword");
        given(userMapper.findByEmailIncludingDeleted(anyString())).willReturn(null);
        given(userMapper.insert(any(User.class))).willReturn(1);

        /* When */
        ResponseEntity<Map<String, Object>> response = userService.join(userJoinDTO, token);

        /* Then */
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        verify(userMapper, times(1)).insert(any(User.class));
    }

    @DisplayName("findByEmail 호출 여부 확인")
    @Test
    void findByEmail_shouldBeCalled() {
        String email = "test@example.com";
        User mockUser = new User();
        given(userMapper.findByEmail(email)).willReturn(mockUser);

        User result = userMapper.findByEmail(email);

        assertThat(result).isEqualTo(mockUser);
        verify(userMapper, times(1)).findByEmail(email);
    }

    @DisplayName("findByEmailIncludingDeleted 호출 여부 확인")
    @Test
    void findByEmailIncludingDeleted_shouldBeCalled() {
        String email = "deleted@example.com";
        User mockUser = new User();
        given(userMapper.findByEmailIncludingDeleted(email)).willReturn(mockUser);

        User result = userMapper.findByEmailIncludingDeleted(email);

        assertThat(result).isEqualTo(mockUser);
        verify(userMapper, times(1)).findByEmailIncludingDeleted(email);
    }

    @DisplayName("findNameById 호출 여부 확인")
    @Test
    void findNameById_shouldBeCalled() {
        int id = 1;
        given(userMapper.findNameById(id)).willReturn("홍길동");

        String name = userMapper.findNameById(id);

        assertThat(name).isEqualTo("홍길동");
        verify(userMapper, times(1)).findNameById(id);
    }

    @DisplayName("findById 호출 여부 확인")
    @Test
    void findById_shouldBeCalled() {
        int id = 2;
        User mockUser = new User();
        given(userMapper.findById(id)).willReturn(mockUser);

        User result = userMapper.findById(id);

        assertThat(result).isEqualTo(mockUser);
        verify(userMapper, times(1)).findById(id);
    }

    @DisplayName("findEmailByNameAndPhone 호출 여부 확인")
    @Test
    void findEmailByNameAndPhone_shouldBeCalled() {
        String name = "홍길동";
        String phone = "01012341234";
        List<String> mockEmails = List.of("test@example.com");
        given(userMapper.findEmailByNameAndPhone(name, phone)).willReturn(mockEmails);

        List<String> result = userMapper.findEmailByNameAndPhone(name, phone);

        assertThat(result).isEqualTo(mockEmails);
        verify(userMapper, times(1)).findEmailByNameAndPhone(name, phone);
    }

    @DisplayName("findUsersDeletedBefore 호출 여부 확인")
    @Test
    void findUsersDeletedBefore_shouldBeCalled() {
        LocalDateTime time = LocalDateTime.now();
        List<User> mockUsers = List.of(new User());
        given(userMapper.findUsersDeletedBefore(time)).willReturn(mockUsers);

        List<User> result = userMapper.findUsersDeletedBefore(time);

        assertThat(result).isEqualTo(mockUsers);
        verify(userMapper, times(1)).findUsersDeletedBefore(time);
    }

    @DisplayName("getUserProfileByUserId 호출 여부 확인")
    @Test
    void getUserProfileByUserId_shouldBeCalled() {
        int id = 3;
        UserProfileResponseDTO profile = new UserProfileResponseDTO();
        given(userMapper.getUserProfileByUserId(id)).willReturn(profile);

        UserProfileResponseDTO result = userMapper.getUserProfileByUserId(id);

        assertThat(result).isEqualTo(profile);
        verify(userMapper, times(1)).getUserProfileByUserId(id);
    }

    @DisplayName("updatePasswordByEmail 호출 여부 확인")
    @Test
    void updatePasswordByEmail_shouldBeCalled() {
        String email = "test@example.com";
        String newPassword = "encodedPass";
        given(userMapper.updatePasswordByEmail(email, newPassword)).willReturn(1);

        int rows = userMapper.updatePasswordByEmail(email, newPassword);

        assertThat(rows).isEqualTo(1);
        verify(userMapper, times(1)).updatePasswordByEmail(email, newPassword);
    }

    @DisplayName("updateProfileById 호출 여부 확인")
    @Test
    void updateProfileById_shouldBeCalled() {
        int id = 4;
        String phone = "01098765432";
        given(userMapper.updateProfileById(id, phone)).willReturn(1);

        int rows = userMapper.updateProfileById(id, phone);

        assertThat(rows).isEqualTo(1);
        verify(userMapper, times(1)).updateProfileById(id, phone);
    }

    @DisplayName("updateInsightCycleById 호출 여부 확인")
    @Test
    void updateInsightCycleById_shouldBeCalled() {
        int id = 5;
        String cycle = "WEEKLY_1";
        given(userMapper.updateInsightCycleById(id, cycle)).willReturn(1);

        int rows = userMapper.updateInsightCycleById(id, cycle);

        assertThat(rows).isEqualTo(1);
        verify(userMapper, times(1)).updateInsightCycleById(id, cycle);
    }

    @DisplayName("updateDeletedDateById 호출 여부 확인")
    @Test
    void updateDeletedDateById_shouldBeCalled() {
        int id = 6;
        given(userMapper.updateDeletedDateById(id)).willReturn(1);

        int rows = userMapper.updateDeletedDateById(id);

        assertThat(rows).isEqualTo(1);
        verify(userMapper, times(1)).updateDeletedDateById(id);
    }

    @DisplayName("deleteUserByEmail 호출 여부 확인")
    @Test
    void deleteUserByEmail_shouldBeCalled() {
        String email = "delete@example.com";
        given(userMapper.deleteUserByEmail(email)).willReturn(1);

        int rows = userMapper.deleteUserByEmail(email);

        assertThat(rows).isEqualTo(1);
        verify(userMapper, times(1)).deleteUserByEmail(email);
    }

    @DisplayName("deleteUserById 호출 여부 확인")
    @Test
    void deleteUserById_shouldBeCalled() {
        int id = 7;
        given(userMapper.deleteUserById(id)).willReturn(1);

        int rows = userMapper.deleteUserById(id);

        assertThat(rows).isEqualTo(1);
        verify(userMapper, times(1)).deleteUserById(id);
    }
}