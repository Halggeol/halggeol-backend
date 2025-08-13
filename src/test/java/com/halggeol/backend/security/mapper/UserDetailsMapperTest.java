package com.halggeol.backend.security.mapper;

import com.halggeol.backend.security.domain.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@DisplayName("UserDetailsMapper Mockito 단위 테스트")
class UserDetailsMapperTest {

    @Mock
    private UserDetailsMapper userDetailsMapper;

    @DisplayName("get - 이메일로 사용자 조회 호출 확인")
    @Test
    void get_shouldBeCalled() {
        /* Given */
        String email = "test@example.com";
        User mockUser = new User();
        mockUser.setEmail(email);
        mockUser.setName("홍길동");

        given(userDetailsMapper.get(email)).willReturn(mockUser);

        /* When */
        User user = userDetailsMapper.get(email);

        /* Then */
        // 결과 검증
        assertThat(user).isNotNull();
        assertThat(user.getEmail()).isEqualTo(email);
        assertThat(user.getName()).isEqualTo("홍길동");

        // 호출 여부 검증
        verify(userDetailsMapper, times(1)).get(email);
    }
}
