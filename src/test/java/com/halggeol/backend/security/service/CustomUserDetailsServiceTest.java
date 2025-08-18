package com.halggeol.backend.security.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import com.halggeol.backend.security.domain.CustomUser;
import com.halggeol.backend.security.domain.User;
import com.halggeol.backend.security.mapper.UserDetailsMapper;
import com.halggeol.backend.security.service.CustomUserDetailsService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@ExtendWith(MockitoExtension.class)
@DisplayName("CustomUserDetailsService Mockito 단위 테스트")
class CustomUserDetailsServiceTest {

    @Mock
    private UserDetailsMapper userDetailsMapper;
    @InjectMocks
    private CustomUserDetailsService customUserDetailsService;

    @DisplayName("loadUserByUsername: 존재하지 않는 사용자 예외")
    @Test
    void loadUserByUsername_failure() {
        // given
        String email = "no-user@example.com";
        when(userDetailsMapper.get(email)).thenReturn(null);

        // when & then
        assertThrows(UsernameNotFoundException.class,
            () -> customUserDetailsService.loadUserByUsername(email));
    }

    @Test
    void loadUserByUsername_success() {
        // given
        String email = "user@example.com";
        User user = new User(); // 필요한 필드 세팅
        user.setEmail(email);
        user.setPassword("password");

        when(userDetailsMapper.get(email)).thenReturn(user);

        // when
        UserDetails result = customUserDetailsService.loadUserByUsername(email);

        // then
        assertTrue(result instanceof CustomUser);
    }
}
