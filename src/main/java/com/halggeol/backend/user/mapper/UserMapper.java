package com.halggeol.backend.user.mapper;

import com.halggeol.backend.security.domain.User;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper {
    User findByEmail(String email);

    int insert(User user);

    String findNameById(int id);

    List<String> findEmailByNameAndPhone(@Param("name") String name, @Param("phone") String phone);

    int updatePassword(
        @Param("email") String email,
        @Param("newPassword") String newPassword
    );

    User findById(int id);
}
