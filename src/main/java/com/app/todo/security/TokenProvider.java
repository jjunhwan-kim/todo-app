package com.app.todo.security;

import com.app.todo.model.UserEntity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Slf4j
@Service
public class TokenProvider {

    @Value("${jwt.secret-key}")
    private String secretKey;

    public String create(UserEntity userEntity) {

        Date expiryDate = Date.from(Instant.now().plus(1, ChronoUnit.DAYS));

        /*
            { // header
                "alg": "HS512"
            }.
            { // payload
                "sub": "",
                "iss": "demo app",
                "iat": 1234,
                "exp": 1234
            }.
            ABCD // Secret Key를 이용해 서명한 부분
        */

        return Jwts.builder()
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .setSubject(userEntity.getId()) // sub
                .setIssuer("demo app")          // iss
                .setIssuedAt(new Date())        // iat
                .setExpiration(expiryDate)      // exp
                .compact();
    }

    public String validateAndGetUserId(String token) {
        // token의 서명과 header와 payload를 Secret Key를 이용해 서명한 값과 비교하여 위조라면 예외 발생
        Claims claims = Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }
}
