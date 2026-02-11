package com.gocle.lxp.security;

import com.gocle.lxp.domain.LoginUser;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    private final Key key;

    public JwtUtil(@Value("${jwt.secret}") String secret) {
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
    }

    private static final long EXPIRATION = 1000 * 60 * 60 * 2;

    public String generateToken(String userId, String role, Long clientId) {
        return Jwts.builder()
            .setSubject(userId)
            .claim("role", role)
            .claim("clientId", clientId)
            .setIssuedAt(new Date())
            .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION))
            .signWith(key, SignatureAlgorithm.HS256)
            .compact();
    }

    public boolean validateToken(String token) {
        try {
            parseClaims(token);
            return true;
        } catch (JwtException e) {
            return false;
        }
    }

    public Claims parseClaims(String token) {
        return Jwts.parserBuilder()
            .setSigningKey(key)
            .build()
            .parseClaimsJws(token)
            .getBody();
    }

    // 🔥 핵심
    public LoginUser extractLoginUser(String token) {
        Claims claims = parseClaims(token);

        return new LoginUser(
            claims.getSubject(),
            claims.get("role", String.class),
            claims.get("clientId", Long.class),
            null // name 은 필요하면 claim 추가
        );
    }
}
