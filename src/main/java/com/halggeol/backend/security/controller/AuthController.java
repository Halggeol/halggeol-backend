package com.halggeol.backend.security.controller;

import com.halggeol.backend.security.domain.CustomUser;
import com.halggeol.backend.security.dto.FindEmailDTO;
import com.halggeol.backend.security.dto.ResetPasswordDTO;
import com.halggeol.backend.security.dto.ReverifyPasswordDTO;
import com.halggeol.backend.security.service.AuthService;
import com.halggeol.backend.user.dto.EmailDTO;
import java.util.HashMap;
import java.util.Map;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class AuthController {
    private final AuthService authService;

    // 로그인 시간 연장 API
    @GetMapping("/login/extend")
    public ResponseEntity<Map<String, String>> extendLogin(
        @AuthenticationPrincipal CustomUser user
    ) {
        return ResponseEntity.ok(authService.extendLogin(user.getUsername()));
    }

    // 아이디 찾기 API
    @PostMapping("/email/find")
    public ResponseEntity<Map<String, Object>> findEmail(
        @Valid @RequestBody FindEmailDTO info
    ) {
        return ResponseEntity.ok(authService.findEmail(info));
    }

    // 로그인 상태 비밀번호 재확인 API
    @PostMapping("/password/reverify")
    public ResponseEntity<Map<String, String>> reverifyPassword(
        @AuthenticationPrincipal CustomUser user,
        @Valid @RequestBody ReverifyPasswordDTO password
    ) {
        return ResponseEntity.ok(authService.reverifyPassword(user, password));
    }

    // 로그인 상태 비밀번호 재설정 API
    @PatchMapping("/password/reset")
    public ResponseEntity<Map<String, String>> resetPasswordWithLogin(
        @AuthenticationPrincipal CustomUser user,
        @Valid @RequestBody ResetPasswordDTO passwords,
        @RequestHeader("Authorization") String bearerToken
    ) {
        return ResponseEntity.ok(authService.resetPasswordWithLogin(user, passwords, bearerToken));
    }

    // 비로그인 상태 비밀번호 재설정 요청 API
    @PostMapping("/password/reset/request")
    public ResponseEntity<Map<String, String>> requestResetPassword(
        @Valid @RequestBody EmailDTO email
    ) {
        return ResponseEntity.ok(authService.requestResetPassword(email));
    }

    // 비로그인 상태 비밀번호 재설정 변경 API
    @PostMapping("/password/reset")
    public ResponseEntity<Map<String, String>> resetPasswordWithoutLogin(
        @Valid @RequestBody ResetPasswordDTO passwords,
        @RequestParam String token
    ) {
        return ResponseEntity.ok(authService.resetPasswordWithoutLogin(passwords, token));
    }
}
