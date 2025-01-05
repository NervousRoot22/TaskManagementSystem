package com.task.management.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;

import java.util.Date;

import io.jsonwebtoken.security.Keys;
import javax.crypto.SecretKey;

@Service
public class JwtUtilService {
    private final SecretKey key = Keys.hmacShaKeyFor("mySecretKeymySecretKeymySecretKeymySecretKey".getBytes());

    public String generateToken(String username, String email) {
        return Jwts.builder()
                .setSubject(username)  // Username as subject
                .claim("email", email)  // Custom claim for email
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))  // 1 hour expiration
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public String extractEmail(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.get("email", String.class);  // Extract the email from claims
    }

}
