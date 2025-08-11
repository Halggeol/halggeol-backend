package com.halggeol.backend.security.service;

import com.halggeol.backend.security.domain.CustomUser;
import com.halggeol.backend.security.dto.FindEmailDTO;
import com.halggeol.backend.security.dto.ResetPasswordDTO;
import com.halggeol.backend.security.dto.ReverifyPasswordDTO;
import com.halggeol.backend.security.mail.domain.MailType;
import com.halggeol.backend.security.mail.dto.MailDTO;
import com.halggeol.backend.security.mail.service.MailService;
import com.halggeol.backend.security.util.JwtManager;
import com.halggeol.backend.user.dto.EmailDTO;
import com.halggeol.backend.user.mapper.UserMapper;
import com.halggeol.backend.user.service.UserService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Log4j2
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final JwtManager jwtManager;
    private final UserMapper userMapper;
    private final Argon2PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final MailService mailService;

    @Override
    public Map<String, String> extendLogin(String email) {
        String newToken = jwtManager.generateAccessToken(email);

        return Map.of(
            "message", "로그인 시간이 연장되었습니다.",
            "accessToken", newToken
        );
    }

    @Override
    public Map<String, Object> findEmail(FindEmailDTO info) {
        List<String> email = userMapper.findEmailByNameAndPhone(info.getName(), info.getPhone());

        if (email == null || email.isEmpty()) {
            return Map.of("message", "일치하는 사용자 정보가 없습니다.");
        }

        List<String> maskedEmail = email.stream()
            .map(this::maskEmail)
            .toList();

        return Map.of(
            "message", "아이디 찾기에 성공했습니다.",
            "email", maskedEmail
        );
    }

    @Override
    public Map<String, String> reverifyPassword(CustomUser user, ReverifyPasswordDTO password) {
        if (!passwordEncoder.matches(password.getConfirmPassword(), user.getPassword())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "비밀번호가 일치하지 않습니다.");
        }
        String newToken = jwtManager.generateReverifyToken(user.getUsername());

        return Map.of(
            "message", "비밀번호 재확인에 성공했습니다.",
            "reverifyToken", newToken
        );
    }

    @Override
    public Map<String, String> resetPasswordWithLogin(CustomUser user, ResetPasswordDTO passwords, String bearerToken) {
        if (!jwtManager.isReverified(jwtManager.parseBearerToken(bearerToken))) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "비밀번호가 재확인되지 않았습니다.");
        }
        if (!passwords.isPasswordConfirmed()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "비밀번호가 일치하지 않습니다.");
        }
        if (passwordEncoder.matches(passwords.getNewPassword(), user.getPassword())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "기존 비밀번호와 동일한 비밀번호는 사용할 수 없습니다.");
        }
        userMapper.updatePasswordByEmail(user.getUsername(), passwordEncoder.encode(passwords.getNewPassword()));
        return Map.of("message", "비밀번호가 변경되었습니다.");
    }

    @Override
    public ResponseEntity<Map<String, Object>> requestResetPassword(EmailDTO email) {
        Map<String, Object> response = new HashMap<>();

        try {
            if (userService.findByEmail(email.getEmail()) == null) {
                response.put("success", false);
                response.put("message", "일치하는 사용자 정보가 없습니다.");
                return ResponseEntity.ok(response);
            }

            mailService.sendMail(
                MailDTO.builder()
                    .email(email.getEmail())
                    .token(jwtManager.generateVerifyToken(email.getEmail()))
                    .mailType(MailType.PASSWORD_RESET)
                    .build()
            );

            response.put("success", true);
            response.put("message", "비밀번호 변경 이메일이 전송되었습니다.");
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "비밀번호 재설정 이메일 전송 중 오류가 발생했습니다: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @Override
    public Map<String, String> resetPasswordWithoutLogin(ResetPasswordDTO passwords, String token) {
        if (!jwtManager.validateToken(token)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "토큰이 유효하지 않습니다.");
        }
        if (!passwords.isPasswordConfirmed()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "비밀번호가 일치하지 않습니다.");
        }

        String email = jwtManager.getEmail(token);
        if (passwordEncoder.matches(passwords.getNewPassword(), userMapper.findByEmail(email).getPassword())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "기존 비밀번호와 동일한 비밀번호는 사용할 수 없습니다.");
        }

        userMapper.updatePasswordByEmail(email, passwordEncoder.encode(passwords.getNewPassword()));
        return Map.of("message", "비밀번호가 변경되었습니다.");
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
