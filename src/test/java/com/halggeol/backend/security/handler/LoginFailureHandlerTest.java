package com.halggeol.backend.security.handler;

import com.halggeol.backend.security.util.JsonResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.Map;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("LoginFailureHandler Mockito 단위 테스트")
class LoginFailureHandlerTest {

    private final LoginFailureHandler handler = new LoginFailureHandler();

    @Test
    @DisplayName("onAuthenticationFailure - BadCredentialsException 시 메시지 변경")
    void onAuthenticationFailure_badCredentials() throws Exception {
        /* Given */
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        AuthenticationException exception = new BadCredentialsException("잘못된 비밀번호");

        try (MockedStatic<JsonResponse> jsonResponseMock = mockStatic(JsonResponse.class)) {

            /* When */
            handler.onAuthenticationFailure(request, response, exception);

            /* Then */
            jsonResponseMock.verify(() ->
                JsonResponse.sendError(
                    response,
                    HttpStatus.UNAUTHORIZED,
                    Map.of("message", "아이디 또는 비밀번호가 올바르지 않습니다: 잘못된 비밀번호")
                )
            );
        }
    }

    @Test
    @DisplayName("onAuthenticationFailure - 일반 AuthenticationException 시 기본 메시지 사용")
    void onAuthenticationFailure_generalException() throws Exception {
        /* Given */
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        AuthenticationException exception = mock(AuthenticationException.class);
        when(exception.getMessage()).thenReturn("토큰이 만료됨");

        try (MockedStatic<JsonResponse> jsonResponseMock = mockStatic(JsonResponse.class)) {

            /* When */
            handler.onAuthenticationFailure(request, response, exception);

            /* Then */
            jsonResponseMock.verify(() ->
                JsonResponse.sendError(
                    response,
                    HttpStatus.UNAUTHORIZED,
                    Map.of("message", "로그인에 실패했습니다: 토큰이 만료됨")
                )
            );
        }
    }
}
