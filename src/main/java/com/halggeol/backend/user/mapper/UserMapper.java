package com.halggeol.backend.user.mapper;

import com.halggeol.backend.security.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper {
    User findByEmail(String email);

    int insert(User user);

    String findNameById(int id);

    String findEmailByNameAndPhone(@Param("name") String name, @Param("phone") String phone);

    int updatePassword(
        @Param("id") int id,
        @Param("newPassword") String newPassword
    );
}
