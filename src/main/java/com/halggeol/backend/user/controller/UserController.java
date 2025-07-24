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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/signup")
public class UserController {
    private final UserService userService;
    private final JwtManager jwtManager;

    @GetMapping("/request")
    public ResponseEntity<Void> requestJoin(UserJoinDTO user) {
        return ResponseEntity.status(userService.requestJoin(user)).build();
    }

    @PostMapping("")
    public ResponseEntity<Void> join(UserJoinDTO user, @RequestParam String token) {
        return ResponseEntity.status(userService.join(user, token)).build();
    }
}
