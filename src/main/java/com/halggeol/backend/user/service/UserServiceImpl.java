package com.halggeol.backend.user.service;

import com.halggeol.backend.domain.CustomUser;
import com.halggeol.backend.domain.User;
import com.halggeol.backend.security.mail.domain.MailType;
import com.halggeol.backend.security.mail.dto.MailDTO;
import com.halggeol.backend.security.mail.service.MailService;
import com.halggeol.backend.security.util.JwtManager;
import com.halggeol.backend.user.dto.UpdateProfileDTO;
import com.halggeol.backend.user.dto.EmailDTO;
import com.halggeol.backend.user.dto.UpdateCycleRequestDTO;
import com.halggeol.backend.user.dto.UserJoinDTO;
import com.halggeol.backend.user.dto.UserProfileResponseDTO;
import com.halggeol.backend.user.mapper.UserMapper;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Log4j2
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserMapper userMapper;
    private final MailService mailService;
    private final JwtManager jwtManager;
    private final Argon2PasswordEncoder passwordEncoder;

    @Override
    public User findByEmail(String email) {
        return userMapper.findByEmail(email);
    }

    @Override
    public void emailExists(String email) {
        if (email == null || email.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "이메일이 입력되지 않았습니다.");
        }
        if (findByEmail(email) == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "존재하지 않는 사용자입니다.");
        }
    }

    @Override
    public ResponseEntity<Map<String, Object>> requestJoin(EmailDTO email) {
        // 입력값 유효성 검증은 UserJoinDTO에서 진행
        // 검증 실패 시 MethodArgumentNotValidException 예외 발생
        // 스프링 MVC에서 자동으로 400 Bad Request로 응답함

        Map<String, Object> response = new HashMap<>();

        try {
            if (findByEmail(email.getEmail()) != null) {
                response.put("success", false);
                response.put("message", "이미 존재하는 사용자입니다.");
                return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
            }

            mailService.sendMail(
                MailDTO.builder()
                    .email(email.getEmail())
                    .token(jwtManager.generateVerifyToken(email.getEmail()))
                    .mailType(MailType.SIGNUP)
                    .build()
            );

            response.put("success", true);
            response.put("message", "본인 인증 이메일이 전송되었습니다.");
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "회원가입 요청 이메일 전송 중 오류가 발생했습니다: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @Transactional
    @Override
    public ResponseEntity<Map<String, Object>> join(UserJoinDTO userToJoin, String token) {
        Map<String, Object> response = new HashMap<>();

        if (!jwtManager.validateToken(token)) {
            response.put("success", false);
            response.put("message", "토큰이 유효하지 않습니다.");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
        if (!userToJoin.isValidAge()) {
            response.put("success", false);
            response.put("message", "만 14세 이상 가입 가능합니다.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        if (!userToJoin.isCorrectPassword()) {
            response.put("success", false);
            response.put("message", "비밀번호가 일치하지 않습니다.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        // soft delete 된 회원 데이터 삭제
        User deleteUser = userMapper.findByEmailIncludingDeleted(userToJoin.getEmail());
        if (deleteUser != null && deleteUser.getDeletedDate() != null) {
            userMapper.deleteUserByEmail(userToJoin.getEmail());
        }

        User user = userToJoin.toVO();
        user.setEmail(jwtManager.getEmail(token));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userMapper.insert(user);

        response.put("success", true);
        response.put("message", "회원가입이 완료되었습니다.");
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Override
    public String getNameById(int userId) {
        // 사용자 ID로 사용자 이름을 조회하는 메서드
        return userMapper.findNameById(userId);
    }

    @Override
    public Map<String, Object> viewProfile(CustomUser user, String scope) {
        UserProfileResponseDTO profile = userMapper.getUserProfileByUserId(user.getUser().getId());
        return Map.of(
            "message", "유저 정보 조회에 성공했습니다.",
            "profile", profile.toMap()
        );
    }

    @Override
    public Map<String, String> updateProfile(CustomUser user, UpdateProfileDTO info) {
        userMapper.updateProfileById(user.getUser().getId(), info.getPhone());
        return Map.of("message", "사용자 정보 수정이 완료되었습니다.");
    }

    @Override
    public Map<String, String> markAsDeleted(CustomUser user, String bearerToken) {
        if (!jwtManager.isReverified(jwtManager.parseBearerToken(bearerToken))) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "비밀번호가 재확인되지 않았습니다.");
        }
        userMapper.updateDeletedDateById(user.getUser().getId());
        return Map.of("message", "회원탈퇴가 완료되었습니다.");
    }

    @Override
    public Map<String, String> updateInsightCycle(CustomUser user, UpdateCycleRequestDTO cycle) {
        cycle.validateCycleType();
        switch (cycle.getCycle()) {
            case "WEEKLY_1" ->
                userMapper.updateInsightCycleById(user.getUser().getId(), "0 0 0 1/7 * *");
            case "WEEKLY_2" ->
                userMapper.updateInsightCycleById(user.getUser().getId(), "0 0 0 1/14 * *");
            case "MONTHLY_1" ->
                userMapper.updateInsightCycleById(user.getUser().getId(), "0 0 0 1 * *");
            default ->
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "설정 가능한 주기가 아닙니다.");
        }
        return Map.of("message", "인사이트 주기 변경이 완료되었습니다.");
    }
}
