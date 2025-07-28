package com.halggeol.backend.security.controller;

import com.halggeol.backend.security.domain.CustomUser;
import com.halggeol.backend.security.service.AuthService;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
//
//    // 아이디 찾기 API
//    @PostMapping("/email/find")
//    public ResponseEntity<Void> findEmail(@RequestBody Email email) {
//    }
//
//    // 로그인 상태 비밀번호 재확인 API
//    @PatchMapping("/password/reverify")
//    public ResponseEntity<Void> reverifyPassword() {
//    }
//
//    // 로그인 상태 비밀번호 재설정 API
//    @PatchMapping("/password/reset")
//    public ResponseEntity<Void> resetPasswordWithLogin() {
//    }
//
//    // 비로그인 상태 비밀번호 재설정 요청 API
//    @PostMapping("/password/reset/request")
//    public ResponseEntity<Void> requestResetPassword() {
//    }
//
//    // 비로그인 상태 비밀번호 재설정 변경 API
//    @PostMapping("/password/reset")
//    public ResponseEntity<Void> resetPasswordWithoutLogin() {
//    }
}
