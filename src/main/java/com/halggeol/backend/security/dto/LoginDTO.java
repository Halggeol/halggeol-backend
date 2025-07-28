package com.halggeol.backend.security.dto;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginDTO {
    private String email;
    private String password;

    public static LoginDTO of(HttpServletRequest request) {
        ObjectMapper mapper = new ObjectMapper();

        try {
            return mapper.readValue(request.getInputStream(), LoginDTO.class);
        } catch (IOException e) {
            throw new BadCredentialsException("email or password is empty");
        }
    }
}
