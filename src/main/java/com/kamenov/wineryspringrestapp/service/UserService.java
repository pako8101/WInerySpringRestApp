package com.kamenov.wineryspringrestapp.service;

import com.kamenov.wineryspringrestapp.models.dto.UserRegisterDto;
import org.springframework.security.core.Authentication;

import java.util.function.Consumer;

public interface UserService {
    void registerUser(UserRegisterDto userRegisterDto, Consumer<Authentication> successfulRegister);

}
