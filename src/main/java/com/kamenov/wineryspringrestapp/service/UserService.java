package com.kamenov.wineryspringrestapp.service;

import com.kamenov.wineryspringrestapp.models.dto.UserRegisterDto;
import com.kamenov.wineryspringrestapp.models.entity.UserEntity;
import com.kamenov.wineryspringrestapp.models.user.AppUserDetails;
import com.kamenov.wineryspringrestapp.models.view.UserViewModel;
import org.springframework.security.core.Authentication;

import java.util.Optional;
import java.util.function.Consumer;

public interface UserService {


    public UserEntity registerUser(UserRegisterDto userRegisterDto,
                            Consumer<Authentication> successfulRegister);

    public UserViewModel getUserProfile();

   public UserViewModel findBId(Long id);

    UserEntity findByName(String username);

    Optional<AppUserDetails> getCurrentUser();
}
