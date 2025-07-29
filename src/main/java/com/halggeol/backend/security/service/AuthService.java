package com.halggeol.backend.security.service;

import com.halggeol.backend.security.domain.CustomUser;
import com.halggeol.backend.security.dto.FindEmailDTO;
import com.halggeol.backend.security.dto.ResetPasswordDTO;
import com.halggeol.backend.security.dto.ReverifyPasswordDTO;
import java.util.Map;
import org.springframework.http.HttpStatus;

public interface AuthService {
    Map<String, String> extendLogin(String email);

    Map<String, Object> findEmail(FindEmailDTO info);

    Map<String, String> resetPasswordWithLogin(CustomUser user, ResetPasswordDTO passwords, String bearerToken);

    Map<String, String> reverifyPassword(CustomUser user, ReverifyPasswordDTO password);
}
