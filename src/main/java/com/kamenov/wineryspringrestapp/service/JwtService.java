package com.kamenov.wineryspringrestapp.service;

import com.kamenov.wineryspringrestapp.models.entity.UserEntity;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Map;

public interface JwtService {

    UserDetails extractUserFromToken(String token);

    String generateToken(UserEntity user);

    String generateTokenValue(Map<String, Object> claims, String username);
}
