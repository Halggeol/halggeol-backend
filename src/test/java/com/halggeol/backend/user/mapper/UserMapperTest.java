package com.halggeol.backend.user.mapper;

import static org.junit.jupiter.api.Assertions.*;

import com.halggeol.backend.global.config.AppConfig;
import com.halggeol.backend.security.account.domain.User;
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
}
