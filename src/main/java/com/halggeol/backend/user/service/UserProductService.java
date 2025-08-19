package com.halggeol.backend.user.service;

import com.halggeol.backend.domain.CustomUser;
import com.halggeol.backend.user.dto.UserProductResponseDTO;
import java.util.List;

public interface UserProductService {
    List<UserProductResponseDTO> getUserProductsByUserId(CustomUser user);
}
