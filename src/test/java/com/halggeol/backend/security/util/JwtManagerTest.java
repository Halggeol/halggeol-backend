package com.halggeol.backend.security.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import static org.assertj.core.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("JwtManager Mockito 단위 테스트")
class JwtManagerTest {

    private JwtManager jwtManager;
    private static final String SECRET_KEY =
        "thisIsASecretKeyForJwtTestsAndMustBeLongEnough123"; // 최소 32바이트 이상

    @BeforeEach
    void setUp() {
        jwtManager = new JwtManager(SECRET_KEY);
    }

    @Test
    void generateAccessToken_and_getEmail_shouldReturnSameEmail() {
        /* Given */
        String email = "test@example.com";

        /* When */
        String token = jwtManager.generateAccessToken(email);
        String parsedEmail = jwtManager.getEmail(token);

        /* Then */
        assertThat(parsedEmail).isEqualTo(email);
    }

    @Test
    void generateVerifyToken_and_validateToken_shouldReturnTrue() {
        /* Given */
        String token = jwtManager.generateVerifyToken("verify@example.com");

        /* When */
        boolean isValid = jwtManager.validateToken(token);

        /* Then */
        assertThat(isValid).isTrue();
    }

    @Test
    void generateReverifyToken_and_isReverified_shouldReturnTrue() {
        /* Given */
        String token = jwtManager.generateReverifyToken("reverify@example.com");

        /* When */
        boolean flag = jwtManager.isReverified(token);

        /* Then */
        assertThat(flag).isTrue();
    }

    @Test
    void validateToken_shouldReturnFalse_whenTokenIsInvalid() {
        /* Given */
        String invalidToken = "invalid.jwt.token";

        /* When */
        boolean isValid = jwtManager.validateToken(invalidToken);

        /* Then */
        assertThat(isValid).isFalse();
    }

    @Test
    void parseBearerToken_shouldReturnToken_whenPrefixIsPresent() {
        /* Given */
        String rawToken = jwtManager.generateAccessToken("bearer@example.com");
        String bearerToken = JwtManager.BEARER_PREFIX + rawToken;

        /* When */
        String parsed = jwtManager.parseBearerToken(bearerToken);

        /* Then */
        assertThat(parsed).isEqualTo(rawToken);
    }

    @Test
    void parseBearerToken_shouldReturnNull_whenPrefixIsMissing() {
        /* Given */
        String rawToken = jwtManager.generateAccessToken("noBearer@example.com");

        /* When */
        String parsed = jwtManager.parseBearerToken(rawToken);

        /* Then */
        assertThat(parsed).isNull();
    }
}
