package com.halggeol.backend.security.service;

import com.halggeol.backend.security.domain.CustomUser;
import com.halggeol.backend.security.dto.FindEmailDTO;
import com.halggeol.backend.security.dto.ResetPasswordDTO;
import com.halggeol.backend.security.util.JwtManager;
import com.halggeol.backend.user.mapper.UserMapper;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.stereotype.Service;

@Log4j2
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final JwtManager jwtManager;
    private final UserMapper userMapper;
    private final Argon2PasswordEncoder passwordEncoder;

    @Override
    public Map<String, String> extendLogin(String email) {
        String newToken = jwtManager.generateAccessToken(email);

        Map<String, String> responseBody = new HashMap<>();
        responseBody.put("accessToken", newToken);

        return responseBody;
    }

    @Override
    public Map<String, String> findEmail(FindEmailDTO info) {
        String email = userMapper.findEmailByNameAndPhone(info.getName(), info.getPhone());

        if (email == null) {
            return null;
        }

        Map<String, String> responseBody = new HashMap<>();
        responseBody.put("email", maskEmail(email));

        return responseBody;
    }

    @Override
    public HttpStatus resetPasswordWithLogin(CustomUser user, ResetPasswordDTO passwords, String bearerToken) {
        if (!jwtManager.isReverified(jwtManager.parseBearerToken(bearerToken))) {
            return HttpStatus.UNAUTHORIZED;
        }
        if (!passwords.isPasswordConfirmed()) {
            return HttpStatus.BAD_REQUEST;
        }
        userMapper.updatePassword(user.getUser().getId(), passwordEncoder.encode(passwords.getNewPassword()));
        return HttpStatus.OK;
    }

    private String maskEmail(String email) {
        int atIndex = email.indexOf("@");

        String prefix = email.substring(0, 1);
        String masked = "***";
        String domain = email.substring(atIndex);

        if (atIndex <= 1) {
            return masked + domain;
        }
        return prefix + masked + domain;
    }
}
