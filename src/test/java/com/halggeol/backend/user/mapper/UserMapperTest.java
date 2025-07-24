package com.halggeol.backend.user.mapper;

import com.halggeol.backend.global.config.AppConfig;
import com.halggeol.backend.security.domain.User;
import java.time.LocalDateTime;
import java.time.Month;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {AppConfig.class})
@Log4j2
class UserMapperTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    void findByEmail() {
        String email = "test@gmail.com";

        User foundUser = userMapper.findByEmail(email);

        Assertions.assertEquals(email, foundUser.getEmail());
    }

    @Test
    void insert() {
        LocalDateTime birth = LocalDateTime.of(1990, Month.JANUARY, 2, 0, 0, 0);
        User user = User.builder()
                        .email("test2@example.com")
                        .name("테스트2")
                        .password("123")
                        .phone("01000000000")
                        .birth(birth)
                        .build();
        userMapper.insert(user);
        User foundUser = userMapper.findByEmail("test2@example.com");

        Assertions.assertEquals("01000000000", foundUser.getPhone());
    }
}
