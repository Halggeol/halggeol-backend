package com.halggeol.backend.user.mapper;

import com.halggeol.backend.user.dto.UserProductResponseDTO;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserProductMapper {
    List<UserProductResponseDTO> getUserProductsByUserId(int userId);
}
