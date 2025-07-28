package com.halggeol.backend.security.service;

import com.halggeol.backend.security.domain.CustomUser;
import com.halggeol.backend.security.dto.FindEmailDTO;
import com.halggeol.backend.security.dto.ResetPasswordDTO;
import java.util.Map;
import org.springframework.http.HttpStatus;

public interface AuthService {
    Map<String, String> extendLogin(String email);

    Map<String, String> findEmail(FindEmailDTO info);

    HttpStatus resetPasswordWithLogin(CustomUser user, ResetPasswordDTO passwords, String bearerToken);
}
