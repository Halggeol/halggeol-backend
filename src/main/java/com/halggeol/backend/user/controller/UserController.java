package com.halggeol.backend.user.controller;

import com.halggeol.backend.security.util.JwtManager;
import com.halggeol.backend.user.dto.UserDTO;
import com.halggeol.backend.user.dto.UserJoinDTO;
import com.halggeol.backend.user.service.UserService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/signup")
public class UserController {
    private final UserService userService;

    // 회원가입 요청 (이메일 본인 인증) API
    @PostMapping("/request")
    public ResponseEntity<Void> requestJoin(@RequestBody UserJoinDTO user) {
        return ResponseEntity.status(userService.requestJoin(user)).build();
    }

    // 회원가입 등록 API
    @PostMapping("")
    public ResponseEntity<Void> join(@RequestBody UserJoinDTO user, @RequestParam String token) {
        return ResponseEntity.status(userService.join(user, token)).build();
    }
}
