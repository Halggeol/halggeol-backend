package com.halggeol.backend.user.controller;

import com.halggeol.backend.security.domain.CustomUser;
import com.halggeol.backend.user.dto.EditProfileDTO;
import com.halggeol.backend.user.dto.EmailDTO;
import com.halggeol.backend.user.dto.KnowledgeSurveyRequestDTO;
import com.halggeol.backend.user.dto.TendencySurveyRequestDTO;
import com.halggeol.backend.user.dto.UserJoinDTO;
import com.halggeol.backend.user.service.UserService;
import java.util.Map;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
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

    // 회원가입 요청 (이메일 본인 인증) API
    @PostMapping("/signup/request")
    public ResponseEntity<Map<String, String>> requestJoin(
        @Valid @RequestBody EmailDTO email
    ) {
        return ResponseEntity.ok(userService.requestJoin(email));
    }

    // 회원가입 등록 API
    @PostMapping("/signup")
    public ResponseEntity<Map<String, String>> join(
        @Valid @RequestBody UserJoinDTO user,
        @RequestParam String token
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.join(user, token));
    }

    @GetMapping("/me")
    public ResponseEntity<Map<String, Object>> viewProfile(
        @AuthenticationPrincipal CustomUser user,
        @RequestParam(required = false) String scope
    ) {
        return ResponseEntity.ok(userService.viewProfile(user, scope));
    }

    @PatchMapping("/me")
    public ResponseEntity<Map<String, String>> editProfile(
        @AuthenticationPrincipal CustomUser user,
        @Valid @RequestBody EditProfileDTO info
    ) {
        return ResponseEntity.ok(userService.editProfile(user, info));
    }

    @DeleteMapping("/me")
    public ResponseEntity<Map<String, String>> deleteUser(
        @AuthenticationPrincipal CustomUser user,
        @RequestHeader("Authorization") String bearerToken
    ) {
        return ResponseEntity.ok(userService.deleteUser(user, bearerToken));
    }

    @PatchMapping("/survey/knowledge")
    public ResponseEntity<Map<String, String>> updateKnowledge(
        @Valid @RequestBody KnowledgeSurveyRequestDTO surveyResult
    ) {
        return ResponseEntity.ok(userService.updateKnowledge(surveyResult));
    }

    @PatchMapping("/survey/tendency")
    public ResponseEntity<Map<String, String>> updateTendency(
        @Valid @RequestBody TendencySurveyRequestDTO surveyResult
    ) {
        return ResponseEntity.ok(userService.updateTendency(surveyResult));
    }
}
