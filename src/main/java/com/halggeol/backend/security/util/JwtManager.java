package com.halggeol.backend.security.util;

import com.halggeol.backend.global.config.AppConfig;
import com.halggeol.backend.user.dto.UserJoinDTO;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtManager {
    static private final long ACCESS_TOKEN_VALID_MILISECOND = 1000L * 60 * 30;
    static private final long VERIFY_TOKEN_VALID_MILISECOND = 1000L * 60 * 5;
    private final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    // 액세스 토큰 생성
    public String generateAccessToken(String email) {
        return Jwts.builder()
            .setSubject(email)
            .setIssuedAt(new Date())
            .setExpiration(new Date(new Date().getTime() + ACCESS_TOKEN_VALID_MILISECOND))
            .signWith(key)
            .compact();
    }

    // 이메일 인증 토큰 생성
    public String generateVerifyToken(String email) {
        return Jwts.builder()
            .setSubject(email)
            .setIssuedAt(new Date())
            .setExpiration(new Date(new Date().getTime() + VERIFY_TOKEN_VALID_MILISECOND))
            .signWith(key)
            .compact();
    }

    // email 추출
    public String getEmail(String token) {
        return Jwts.parserBuilder()
            .setSigningKey(key)
            .build()
            .parseClaimsJws(token)
            .getBody()
            .getSubject();
    }

    // 토큰 유효성 검증
    public void validateToken(String token) {
        Jws<Claims> claims = Jwts.parserBuilder()
            .setSigningKey(key)
            .build()
            .parseClaimsJws(token);
    }
}
