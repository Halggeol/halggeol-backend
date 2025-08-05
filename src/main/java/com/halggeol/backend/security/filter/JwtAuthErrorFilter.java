package com.halggeol.backend.security.filter;

import com.halggeol.backend.security.util.JsonResponse;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import java.io.IOException;
import java.util.Map;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Log4j2
@Component
// JWT 파싱과 인증 과정에서 발생하는 예외
public class JwtAuthErrorFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(
        HttpServletRequest request,
        HttpServletResponse response,
        FilterChain chain
    ) throws IOException, ServletException {
        String message = null;
        HttpStatus status = null;

        try {
            super.doFilter(request, response, chain);
        } catch (ExpiredJwtException exception) {
            message = "토큰이 만료되었습니다: " + exception.getMessage();
            status = HttpStatus.UNAUTHORIZED;
        } catch (SignatureException exception) {
            message = "토큰 서명이 유효하지 않습니다: " + exception.getMessage();
            status = HttpStatus.UNAUTHORIZED;
        } catch (SecurityException | IllegalArgumentException exception) {
            message = "유효하지 않은 토큰입니다: " + exception.getMessage();
            status = HttpStatus.UNAUTHORIZED;
        } catch (MalformedJwtException exception) {
            message = "토큰 형식이 잘못되었습니다: " + exception.getMessage();
            status = HttpStatus.BAD_REQUEST;
        } catch (UnsupportedJwtException exception) {
            message = "지원되지 않는 토큰입니다: " + exception.getMessage();
            status = HttpStatus.BAD_REQUEST;
        } catch (ServletException exception) {
            message = "요청 처리 중 오류가 발생했습니다: " + exception.getMessage();
            status = HttpStatus.BAD_REQUEST;
        } catch (Exception exception) {
            message = "서버 내부 오류가 발생했습니다: " + exception.getMessage();
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        if (message != null) {
            JsonResponse.sendError(response, status, Map.of("message", message));
        }
    }
}
