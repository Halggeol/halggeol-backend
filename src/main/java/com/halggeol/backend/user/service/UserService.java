package com.halggeol.backend.user.service;

import com.halggeol.backend.security.domain.CustomUser;
import com.halggeol.backend.user.dto.UpdateProfileDTO;
import com.halggeol.backend.user.dto.EmailDTO;
import com.halggeol.backend.user.dto.UpdateCycleRequestDTO;
import com.halggeol.backend.user.dto.UserJoinDTO;
import java.util.Map;

public interface UserService {
    boolean findByEmail(String email);

    void emailExists(String email);

    Map<String, String> requestJoin(EmailDTO email);

    Map<String, String> join(UserJoinDTO user, String token);

    String getNameById(int userId);

    Map<String, Object> viewProfile(CustomUser user, String scope);

    Map<String, String> updateProfile(CustomUser user, UpdateProfileDTO info);

    Map<String, String> deleteUser(CustomUser user, String bearerToken);

    Map<String, String> updateInsightCycle(CustomUser user, UpdateCycleRequestDTO cycle);
}
