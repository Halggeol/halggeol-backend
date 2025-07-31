package com.halggeol.backend.user.mapper;

import com.halggeol.backend.security.domain.User;
import com.halggeol.backend.user.dto.UserProfileResponseDTO;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper {
    User findByEmail(String email);

    String findNameById(int id);

    User findById(int id);

    List<String> findEmailByNameAndPhone(
        @Param("name") String name,
        @Param("phone") String phone
    );

    UserProfileResponseDTO getUserProfileByUserId(int id);

    int insert(User user);

    int updatePasswordByEmail(
        @Param("email") String email,
        @Param("newPassword") String newPassword
    );

    int updateProfileById(
        @Param("id") int id,
        @Param("phone") String phone
    );

    int deleteUserById(int id);
}
