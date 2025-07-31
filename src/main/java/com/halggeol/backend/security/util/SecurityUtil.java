package com.halggeol.backend.security.util;

import com.halggeol.backend.security.domain.CustomUser;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Log4j2
@Component
public class SecurityUtil {
    public static CustomUser getLoggedUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null
            || !auth.isAuthenticated()
            || auth instanceof AnonymousAuthenticationToken
        ) {
            return null;
        }
        return (CustomUser) auth.getPrincipal();
    }

    public String resolveEmail(String email) {
        CustomUser user = getLoggedUser();
        return user != null ? user.getUser().getEmail() : email;
    }
}
