package com.halggeol.backend.security.filter;

import com.halggeol.backend.security.dto.LoginDTO;
import com.halggeol.backend.security.handler.LoginFailureHandler;
import com.halggeol.backend.security.handler.LoginSuccessHandler;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

@Log4j2
@Component
public class JwtLoginAuthFilter extends UsernamePasswordAuthenticationFilter {
    public JwtLoginAuthFilter(
        AuthenticationManager authManager,
        LoginSuccessHandler loginSuccessHandler,
        LoginFailureHandler loginFailureHandler
    ) {
        super(authManager);
        setFilterProcessesUrl("/api/login");
        setAuthenticationSuccessHandler(loginSuccessHandler);
        setAuthenticationFailureHandler(loginFailureHandler);
    }

    @Override
    public Authentication attemptAuthentication(
        HttpServletRequest request,
        HttpServletResponse response
    ) {
        LoginDTO loginDTO = LoginDTO.of(request);
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                loginDTO.getUsername(),
                loginDTO.getPassword()
            );
        return getAuthenticationManager().authenticate(authToken);
    }
}
