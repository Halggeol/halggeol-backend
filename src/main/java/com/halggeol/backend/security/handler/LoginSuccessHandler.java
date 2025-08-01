package com.halggeol.backend.security.handler;

import com.halggeol.backend.security.domain.CustomUser;
import com.halggeol.backend.security.dto.AuthResultDTO;
import com.halggeol.backend.security.util.JsonResponse;
import com.halggeol.backend.security.util.JwtManager;
import java.io.IOException;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Log4j2
@Component
@RequiredArgsConstructor
public class LoginSuccessHandler implements AuthenticationSuccessHandler {
    private final JwtManager jwtManager;

    private AuthResultDTO makeAuthResult(CustomUser user) {
        String email = user.getUsername();
        String token = jwtManager.generateAccessToken(email);
        return new AuthResultDTO(token, user.getUser().getName());
    }

    @Override
    public void onAuthenticationSuccess(
        HttpServletRequest request,
        HttpServletResponse response,
        Authentication authentication
    ) throws IOException, ServletException {
        CustomUser user = (CustomUser) authentication.getPrincipal();
        AuthResultDTO authResult = makeAuthResult(user);
        JsonResponse.send(response, Map.of(
            "message", "로그인 되었습니다.",
            "authResult", authResult
        ));
    }
}
