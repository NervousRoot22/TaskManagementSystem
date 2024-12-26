package com.task.management.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;

import java.util.Date;

import io.jsonwebtoken.security.Keys;
import javax.crypto.SecretKey;

@Service
public class JwtUtilService {
    private final SecretKey key = Keys.hmacShaKeyFor("mySecretKeymySecretKeymySecretKeymySecretKey".getBytes());

    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))  // 1 hour expiration
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }
}
