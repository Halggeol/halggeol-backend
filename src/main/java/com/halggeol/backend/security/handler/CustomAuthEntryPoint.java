package com.halggeol.backend.security.handler;

import com.halggeol.backend.security.util.JsonResponse;
import java.io.IOException;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Log4j2
@Component
// Spring Security가 인증 실패를 감지했을 경우 호출
public class CustomAuthEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(
        HttpServletRequest request,
        HttpServletResponse response,
        AuthenticationException exception
    ) throws IOException, ServletException {
        String message = "인증에 실패했습니다.";
        HttpStatus status = HttpStatus.UNAUTHORIZED;

        JsonResponse.sendError(response, status, Map.of("message", message));
        log.error(exception.getMessage());
    }
}
