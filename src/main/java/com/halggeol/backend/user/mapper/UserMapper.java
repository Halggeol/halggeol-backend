package com.halggeol.backend.user.mapper;

import com.halggeol.backend.security.domain.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    User findByEmail(String email);

    int insert(User user);

    String findNameById(int id);
}
