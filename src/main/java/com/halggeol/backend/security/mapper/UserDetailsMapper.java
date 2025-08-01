package com.halggeol.backend.security.mapper;

import com.halggeol.backend.security.domain.User;
import org.apache.ibatis.annotations.Mapper;

// 로그인 시 필요한 DB 연동

@Mapper
public interface UserDetailsMapper {
    User get(String username);
}
