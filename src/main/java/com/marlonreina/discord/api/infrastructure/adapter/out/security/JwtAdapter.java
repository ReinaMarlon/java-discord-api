package com.marlonreina.discord.api.infrastructure.adapter.out.security;

import com.marlonreina.discord.api.domain.model.User;
import com.marlonreina.discord.api.domain.port.out.JwtPort;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtAdapter implements JwtPort {

    @Value("${spring.jwt-secret}")
    private String secret;

    private static final long EXPIRATION_TIME = 86400000L;

    @Override
    public String generateToken(User user) {
        return Jwts.builder()
                .setSubject(user.getId())
                .claim("username", user.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    @Override
    public String extractUserId(String token) {
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
}
