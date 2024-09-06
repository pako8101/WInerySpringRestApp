package com.kamenov.wineryspringrestapp.service;

import com.kamenov.wineryspringrestapp.models.dto.UserRegisterDto;
import com.kamenov.wineryspringrestapp.models.entity.UserEntity;
import com.kamenov.wineryspringrestapp.models.view.UserViewModel;
import org.springframework.security.core.Authentication;

import java.util.function.Consumer;

public interface UserService {


    public void registerUser(UserRegisterDto userRegisterDto,
                            Consumer<Authentication> successfulRegister);

    public UserViewModel getUserProfile();

   public UserViewModel findBId(Long id);
}
