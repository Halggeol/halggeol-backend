package com.halggeol.backend.security.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.Writer;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;

public class JsonResponse {
    public static <T> void send(HttpServletResponse response, T result) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        response.setContentType("application/json;charset=UTF-8");
        Writer out = response.getWriter();
        out.write(mapper.writeValueAsString(result));
        out.flush();
    }

    public static <T> void sendError(HttpServletResponse response,
                                    HttpStatus status,
                                    T result ) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        response.setStatus(status.value());
        response.setContentType("application/json;charset=UTF-8");
        Writer out = response.getWriter();
        out.write(mapper.writeValueAsString(result));
        out.flush();
    }
}
