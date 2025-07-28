package com.halggeol.backend.security.service;

import com.halggeol.backend.security.util.JwtManager;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Log4j2
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final JwtManager jwtManager;

    @Override
    public Map<String, String> extendLogin(String email) {
        String newToken = jwtManager.generateAccessToken(email);
        Map<String, String> responseBody = new HashMap<>();
        responseBody.put("accessToken", newToken);

        return responseBody;
    }
}
