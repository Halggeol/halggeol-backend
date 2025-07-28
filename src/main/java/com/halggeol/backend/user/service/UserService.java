package com.halggeol.backend.user.service;

import com.halggeol.backend.user.dto.EmailDTO;
import com.halggeol.backend.user.dto.UserJoinDTO;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;

public interface UserService {
    boolean findByEmail(String email);

    HttpStatus requestJoin(@Valid EmailDTO email);

    HttpStatus join(@Valid UserJoinDTO user, String token);

    String getNameById(int userId);
}
