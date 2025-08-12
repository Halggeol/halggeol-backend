package com.halggeol.backend.security.service;

import com.halggeol.backend.security.domain.CustomUser;
import com.halggeol.backend.security.dto.FindEmailDTO;
import com.halggeol.backend.security.dto.ResetPasswordDTO;
import com.halggeol.backend.security.dto.ReverifyPasswordDTO;
import com.halggeol.backend.user.dto.EmailDTO;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public interface AuthService {
    Map<String, String> extendLogin(String email);

    Map<String, Object> findEmail(FindEmailDTO info);

    Map<String, String> reverifyPassword(CustomUser user, ReverifyPasswordDTO password);

    Map<String, String> resetPasswordWithLogin(CustomUser user, ResetPasswordDTO passwords, String bearerToken);

    ResponseEntity<Map<String, Object>> requestResetPassword(EmailDTO email);

    Map<String, String> resetPasswordWithoutLogin(ResetPasswordDTO passwords, String token);
}
