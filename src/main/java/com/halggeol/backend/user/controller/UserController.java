package com.halggeol.backend.user.controller;

import com.halggeol.backend.security.domain.CustomUser;
import com.halggeol.backend.user.dto.UpdateProfileDTO;
import com.halggeol.backend.user.dto.EmailDTO;
import com.halggeol.backend.user.dto.UpdateCycleRequestDTO;
import com.halggeol.backend.user.dto.UserJoinDTO;
import com.halggeol.backend.user.service.UserService;
import java.util.Map;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {
    private final UserService userService;

    @PostMapping("/signup/request")
    public ResponseEntity<Map<String, String>> requestJoin(
        @Valid @RequestBody EmailDTO email
    ) {
        // 회원가입 요청 (이메일 본인 인증)
        return ResponseEntity.ok(userService.requestJoin(email));
    }

    @PostMapping("/signup")
    public ResponseEntity<Map<String, String>> join(
        @Valid @RequestBody UserJoinDTO user,
        @RequestParam String token
    ) {
        // 회원가입 등록 (회원 정보 저장)
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.join(user, token));
    }

    @GetMapping("/me")
    public ResponseEntity<Map<String, Object>> viewProfile(
        @AuthenticationPrincipal CustomUser user,
        @RequestParam(required = false) String scope
    ) {
        // 마이페이지 회원 정보 조회
        return ResponseEntity.ok(userService.viewProfile(user, scope));
    }

    @PatchMapping("/me")
    public ResponseEntity<Map<String, String>> updateProfile(
        @AuthenticationPrincipal CustomUser user,
        @Valid @RequestBody UpdateProfileDTO info
    ) {
        // 마이페이지 회원 정보 수정
        return ResponseEntity.ok(userService.updateProfile(user, info));
    }

    @DeleteMapping("/me")
    public ResponseEntity<Map<String, String>> deleteUser(
        @AuthenticationPrincipal CustomUser user,
        @RequestHeader("Authorization") String bearerToken
    ) {
        // 회원 탈퇴
        return ResponseEntity.ok(userService.deleteUser(user, bearerToken));
    }

    @PatchMapping("/me/update/cycle")
    public ResponseEntity<Map<String, String>> updateInsightCycle(
        @AuthenticationPrincipal CustomUser user,
        @Valid @RequestBody UpdateCycleRequestDTO cycle
    ) {
        // 인사이트 발행 주기 변경
        return ResponseEntity.ok(userService.updateInsightCycle(user, cycle));
    }
}
