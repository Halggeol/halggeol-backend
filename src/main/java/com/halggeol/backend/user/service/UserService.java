package com.halggeol.backend.user.service;

import com.halggeol.backend.security.domain.CustomUser;
import com.halggeol.backend.user.dto.EditProfileDTO;
import com.halggeol.backend.user.dto.EmailDTO;
import com.halggeol.backend.user.dto.UserJoinDTO;
import java.util.Map;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public interface UserService {
    boolean findByEmail(String email);

    Map<String, String> requestJoin(EmailDTO email);

    Map<String, String> join(UserJoinDTO user, String token);

    String getNameById(int userId);

    Map<String, String> editProfile(CustomUser user, EditProfileDTO info);

    Map<String, String> deleteUser(CustomUser user, String bearerToken);
}
