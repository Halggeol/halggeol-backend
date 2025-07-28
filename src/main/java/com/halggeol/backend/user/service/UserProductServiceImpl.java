package com.halggeol.backend.user.service;

import com.halggeol.backend.user.dto.UserProductResponseDTO;
import com.halggeol.backend.user.mapper.UserProductMapper;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserProductServiceImpl implements UserProductService{
    private final UserProductMapper userProductMapper;

    public List<UserProductResponseDTO> getUserProductsByUserId(int userId) {
        return userProductMapper.getUserProductsByUserId(userId);
    }

}
