package com.halggeol.backend.security.handler;

import com.halggeol.backend.security.util.JsonResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.AccessDeniedException;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("CustomAccessDeniedHandler Mockito 단위 테스트")
class CustomAccessDeniedHandlerTest {

    private final CustomAccessDeniedHandler handler = new CustomAccessDeniedHandler();

    @Test
    @DisplayName("handle - AccessDeniedException 발생 시 JsonResponse 호출")
    void handle_callsJsonResponse() throws Exception {
        /* Given */
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        AccessDeniedException exception = new AccessDeniedException("권한 없음");

        // JsonResponse.sendError는 static 메서드이므로 MockedStatic 사용
        try (MockedStatic<JsonResponse> jsonResponseMock = mockStatic(JsonResponse.class)) {

            /* When */
            handler.handle(request, response, exception);

            /* Then */
            jsonResponseMock.verify(() ->
                JsonResponse.sendError(response, HttpStatus.FORBIDDEN, "권한 없음")
            );
        }
    }
}