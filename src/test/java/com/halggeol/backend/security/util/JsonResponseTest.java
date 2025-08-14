package com.halggeol.backend.security.util;

import static org.mockito.Mockito.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
@DisplayName("JsonResponse Mockito 단위 테스트")
class JsonResponseTest {

    private HttpServletResponse response;
    private StringWriter stringWriter;
    private PrintWriter printWriter;

    @BeforeEach
    void setUp() throws IOException {
        response = mock(HttpServletResponse.class);
        stringWriter = new StringWriter();
        printWriter = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(printWriter);
    }

    @Test
    void send_shouldWriteJsonToResponse() throws IOException {
        /* Given */
        TestDto dto = new TestDto("hello", 123);

        /* When */
        JsonResponse.send(response, dto);

        /* Then */
        printWriter.flush(); // flush해서 StringWriter에 데이터 반영
        String actualJson = stringWriter.toString();
        String expectedJson = new ObjectMapper().writeValueAsString(dto);

        assertThat(actualJson).isEqualTo(expectedJson);
        verify(response).setContentType("application/json;charset=UTF-8");
    }

    @Test
    void sendError_shouldSetStatusAndWriteJsonToResponse() throws IOException {
        /* Given */
        TestDto dto = new TestDto("error", 404);

        /* When */
        JsonResponse.sendError(response, HttpStatus.NOT_FOUND, dto);

        /* Then */
        printWriter.flush();
        String actualJson = stringWriter.toString();
        String expectedJson = new ObjectMapper().writeValueAsString(dto);

        assertThat(actualJson).isEqualTo(expectedJson);
        verify(response).setStatus(HttpStatus.NOT_FOUND.value());
        verify(response).setContentType("application/json;charset=UTF-8");
    }

    // 테스트용 DTO
    static class TestDto {
        public String message;
        public int code;

        public TestDto(String message, int code) {
            this.message = message;
            this.code = code;
        }
    }
}
