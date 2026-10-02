package com.choi.securitystudy.util;

import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;


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


}
