package com.halggeol.backend.domain;

import java.util.List;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

@Getter
@Setter
// userdetails.User: Security 내에서 회원정보를 담는 객체
public class CustomUser extends org.springframework.security.core.userdetails.User {
    private User user;

    public CustomUser(User userVO) {
        super(
            userVO.getEmail(),
            userVO.getPassword(),
            List.of(new SimpleGrantedAuthority("ROLE_USER"))
        );
//        super(userVO.getEmail(), userVO.getPassword(), userVO.getAuthList());
        this.user = userVO;
    }
}
