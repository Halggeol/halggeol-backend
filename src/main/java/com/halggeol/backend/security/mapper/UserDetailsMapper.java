package com.halggeol.backend.security.mapper;

import com.halggeol.backend.security.domain.User;

// 로그인 시 필요한 DB 연동
public interface UserDetailsMapper {
    User get(String username);
}
