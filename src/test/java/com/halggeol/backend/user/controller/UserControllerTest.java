package com.halggeol.backend.user.controller;

import static org.junit.jupiter.api.Assertions.*;

import com.halggeol.backend.global.config.AppConfig;
import com.halggeol.backend.user.dto.UserJoinDTO;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {AppConfig.class})
@Log4j2
class UserControllerTest {

    @Autowired
    private UserController userController;

//    @Test
//    void requestJoin_whenEmailNotExists_shouldReturnOk() {
//        UserJoinDTO user = new UserJoinDTO();
//        user.setEmail("new@example.com");
//
//        ResponseEntity<Void> response = userController.requestJoin(user);
//
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertNull(response.getBody());
//    }
//
//    @Test
//    void requestJoin_whenEmailExists_shouldReturnConflict() {
//        UserJoinDTO user = new UserJoinDTO();
//        user.setEmail("test@gmail.com");
//
//        ResponseEntity<Void> response = userController.requestJoin(user);
//
//        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
//        assertNull(response.getBody());
//    }
}