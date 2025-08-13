package com.halggeol.backend.security.handler;

import com.halggeol.backend.security.domain.CustomUser;
import com.halggeol.backend.security.domain.User;
import com.halggeol.backend.security.dto.AuthResultDTO;
import com.halggeol.backend.security.util.JsonResponse;
import com.halggeol.backend.security.util.JwtManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.Map;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("LoginSuccessHandler Mockito 단위 테스트")
class LoginSuccessHandlerTest {

    private final JwtManager jwtManager = mock(JwtManager.class);
    private final LoginSuccessHandler handler = new LoginSuccessHandler(jwtManager);

    @Test
    @DisplayName("onAuthenticationSuccess - 정상 호출")
    void onAuthenticationSuccess_success() throws Exception {
        /* Given */
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        Authentication authentication = mock(Authentication.class);

        User userEntity = new User();
        userEntity.setName("홍길동");

        CustomUser customUser = mock(CustomUser.class);
        when(customUser.getUsername()).thenReturn("test@example.com");
        when(customUser.getUser()).thenReturn(userEntity);
        when(authentication.getPrincipal()).thenReturn(customUser);

        when(jwtManager.generateAccessToken("test@example.com")).thenReturn("accessToken123");

        try (MockedStatic<JsonResponse> jsonResponseMock = mockStatic(JsonResponse.class)) {

            /* When */
            handler.onAuthenticationSuccess(request, response, authentication);

            /* Then */
            jsonResponseMock.verify(() ->
                JsonResponse.send(response, Map.of(
                    "message", "로그인 되었습니다.",
                    "authResult", new AuthResultDTO("accessToken123", "홍길동")
                ))
            );
        }
    }
}
