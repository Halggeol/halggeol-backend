package com.halggeol.backend.user.service;

import com.halggeol.backend.security.domain.User;
import com.halggeol.backend.security.mail.domain.MailType;
import com.halggeol.backend.security.mail.dto.MailDTO;
import com.halggeol.backend.security.mail.service.MailService;
import com.halggeol.backend.security.util.JwtManager;
import com.halggeol.backend.user.dto.EmailDTO;
import com.halggeol.backend.user.dto.UserJoinDTO;
import com.halggeol.backend.user.mapper.UserMapper;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public HttpStatus requestJoin(@Valid EmailDTO email) {
        // 입력값 유효성 검증은 UserJoinDTO에서 진행
        // 검증 실패 시 MethodArgumentNotValidException 예외 발생
        // 스프링 MVC에서 자동으로 400 Bad Request로 응답함

        if (findByEmail(email.getEmail())) {
            return HttpStatus.CONFLICT;
        }

        mailService.sendMail(MailDTO.builder()
                                    .email(email.getEmail())
                                    .token(jwtManager.generateVerifyToken(email.getEmail()))
                                    .mailType(MailType.SIGNUP)
                                    .build());

        return HttpStatus.OK;
    }

    @Transactional
    @Override
    public HttpStatus join(@Valid UserJoinDTO userToJoin, String token) {
        jwtManager.validateToken(token);

        if (!userToJoin.isValidAge() || !userToJoin.isCorrectPassword()) {
            return HttpStatus.BAD_REQUEST;
        }

        User user = userToJoin.toVO();
        user.setEmail(jwtManager.getEmail(token));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userMapper.insert(user);

        return HttpStatus.OK;
    }

    @Override
    public String getNameById(int userId) {
        // 사용자 ID로 사용자 이름을 조회하는 메서드
        return userMapper.findNameById(userId);
    }
}
