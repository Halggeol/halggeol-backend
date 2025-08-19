package com.halggeol.backend.user.service;

import com.halggeol.backend.domain.CustomUser;
import com.halggeol.backend.domain.User;
import com.halggeol.backend.user.dto.UpdateProfileDTO;
import com.halggeol.backend.user.dto.EmailDTO;
import com.halggeol.backend.user.dto.UpdateCycleRequestDTO;
import com.halggeol.backend.user.dto.UserJoinDTO;
import java.util.Map;
import org.springframework.http.ResponseEntity;

public interface UserService {
    User findByEmail(String email);

    void emailExists(String email);

    ResponseEntity<Map<String, Object>> requestJoin(EmailDTO email);

    ResponseEntity<Map<String, Object>> join(UserJoinDTO user, String token);

    String getNameById(int userId);

    Map<String, Object> viewProfile(CustomUser user, String scope);

    Map<String, String> updateProfile(CustomUser user, UpdateProfileDTO info);

    Map<String, String> markAsDeleted(CustomUser user, String bearerToken);

    Map<String, String> updateInsightCycle(CustomUser user, UpdateCycleRequestDTO cycle);
}
