package com.halggeol.backend.user.service;

import com.halggeol.backend.security.domain.CustomUser;
import com.halggeol.backend.security.domain.User;
import com.halggeol.backend.security.mail.domain.MailType;
import com.halggeol.backend.security.mail.dto.MailDTO;
import com.halggeol.backend.security.mail.service.MailService;
import com.halggeol.backend.security.util.JwtManager;
import com.halggeol.backend.user.dto.EditProfileDTO;
import com.halggeol.backend.user.dto.EmailDTO;
import com.halggeol.backend.user.dto.UserJoinDTO;
import com.halggeol.backend.user.mapper.UserMapper;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
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
    public boolean findByEmail(String email) {
        User user = userMapper.findByEmail(email);
        return user != null;
    }

    @Override
    public Map<String, String> requestJoin(EmailDTO email) {
        // 입력값 유효성 검증은 UserJoinDTO에서 진행
        // 검증 실패 시 MethodArgumentNotValidException 예외 발생
        // 스프링 MVC에서 자동으로 400 Bad Request로 응답함

        if (findByEmail(email.getEmail())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "이미 존재하는 사용자입니다.");
        }

        mailService.sendMail(MailDTO.builder()
                                    .email(email.getEmail())
                                    .token(jwtManager.generateVerifyToken(email.getEmail()))
                                    .mailType(MailType.SIGNUP)
                                    .build());

        return Map.of("Message", "본인 인증 이메일이 전송되었습니다.");
    }

    @Transactional
    @Override
    public Map<String, String> join(UserJoinDTO userToJoin, String token) {
        if (!jwtManager.validateToken(token)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "토큰이 유효하지 않습니다.");
        }
        if (!userToJoin.isValidAge()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "만 14세 이상 가입 가능합니다.");
        }
        if (!userToJoin.isCorrectPassword()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "비밀번호가 일치하지 않습니다.");
        }

        User user = userToJoin.toVO();
        user.setEmail(jwtManager.getEmail(token));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userMapper.insert(user);

        return Map.of("Message", "회원가입이 완료되었습니다.");
    }

    @Override
    public String getNameById(int userId) {
        // 사용자 ID로 사용자 이름을 조회하는 메서드
        return userMapper.findNameById(userId);
    }

    @Override
    public Map<String, String> editProfile(CustomUser user, EditProfileDTO info) {
        userMapper.updateProfileById(user.getUser().getId(), info.getPhone());
        return Map.of("Message", "사용자 정보 수정이 완료되었습니다.");
    }

    @Override
    public Map<String, String> deleteUser(CustomUser user, String bearerToken) {
        if (!jwtManager.isReverified(jwtManager.parseBearerToken(bearerToken))) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "비밀번호가 재확인되지 않았습니다.");
        }
        userMapper.deleteUserById(user.getUser().getId());
        return Map.of("Message", "회원탈퇴가 완료되었습니다.");
    }
}
