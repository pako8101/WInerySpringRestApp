package com.kamenov.wineryspringrestapp.service;

import com.kamenov.wineryspringrestapp.models.entity.UserEntity;
import org.springframework.security.core.userdetails.UserDetails;

public interface JwtService {

    UserDetails extractUserFromToken(String token);

    String generateToken(UserEntity user);
}
