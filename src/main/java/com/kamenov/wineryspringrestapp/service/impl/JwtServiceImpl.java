package com.kamenov.wineryspringrestapp.service.impl;

import com.kamenov.wineryspringrestapp.models.entity.UserEntity;
import com.kamenov.wineryspringrestapp.service.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class JwtServiceImpl implements JwtService {
    private final String jwtSecret;
    private static final String JWT_SECRET_KEY = "WSdv1kEE1tCT1a8ihRSqfwMNzlPBq8IWSdv1kEE1tCT1a8ihRSqfwMNzlPBq8IWSdv1kEE1tCT1a8ihRSqfwMNzlPBq8I";

    public JwtServiceImpl(@Value("${jwt.secret}")
                          String jwtSecret) {
        this.jwtSecret = jwtSecret;
    }
    @Override
    public UserDetails extractUserFromToken(String token) {
        Claims claims = extractClaims(token);

        String userName = getUserName(claims);
        List<String> roles = getRoles(claims);

        return new User(userName, "", roles
                .stream()
                .map(SimpleGrantedAuthority::new)
                .toList()
        );
    }

    @Override
    public String generateToken(UserEntity user) {
        return "Bearer-" + generateTokenValue(new HashMap<>(), user.getUsername());
    }

    @SuppressWarnings("unchecked")
    private static List<String> getRoles(Claims claims) {
        return claims.get("roles", List.class);
    }

    private static String getUserName(Claims claims) {
        return claims.getSubject();
    }

    private Claims extractClaims(String jwtToken) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(jwtToken)
                .getBody();
    }

    private Key getSigningKey() {
        byte[] keyBytes = jwtSecret.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }
    @Override
    public String generateTokenValue(Map<String, Object> claims, String username) {
        return Jwts
                .builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24))
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();
    }
    private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(JWT_SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}