package com.choi.securitystudy.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;


@Component
public class JwtUtil {

    private final SecretKey key;

    private final long accessTokenExpireTime;

    public JwtUtil(
            @Value("${jwt.secret}") String secret,
            @Value("${jwt.access-token-expire-time}") long accessTokenExpireTime
    ) {
        /*
        secret의 길이가
        32 바이트 미만 WeakKeyException,
        32 ~ 47 바이트 -> HS256,
        48 ~ 63 바이트 -> HS384,
        64 바이트 이상 -> HS512
        */
        this.key = Keys.hmacShaKeyFor(
                secret.getBytes(StandardCharsets.UTF_8)
        );

        this.accessTokenExpireTime = accessTokenExpireTime;
    }

    // JWT 발급
    public String createAccessToken(String username, String role){
        Date now = new Date();
        Date expireDate = new Date(now.getTime() + accessTokenExpireTime);

        return Jwts.builder()
                .subject(username)
                .claim("role", role)
                .claim("tokenType", "ACCESS")
                .issuedAt(now)
                .expiration(expireDate)
                .signWith(key)
                .compact();
    }

    // JWT 검증 후 파싱
    public Claims getClaims(String token){
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

}
