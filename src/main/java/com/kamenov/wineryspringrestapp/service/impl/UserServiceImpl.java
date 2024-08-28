package com.kamenov.wineryspringrestapp.service.impl;

import com.kamenov.wineryspringrestapp.models.dto.UserRegisterDto;
import com.kamenov.wineryspringrestapp.models.entity.UserEntity;
import com.kamenov.wineryspringrestapp.models.user.UserSession;
import com.kamenov.wineryspringrestapp.models.view.UserViewModel;
import com.kamenov.wineryspringrestapp.repository.UserRepository;
import com.kamenov.wineryspringrestapp.service.ProfileImageService;
import com.kamenov.wineryspringrestapp.service.RoleService;
import com.kamenov.wineryspringrestapp.service.UserService;
import com.kamenov.wineryspringrestapp.web.RegisterController;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.function.Consumer;
@Service
public class UserServiceImpl implements UserService {
private final UserRepository userRepository;
private final PasswordEncoder passwordEncoder;
private final ModelMapper modelMapper;
    private final UserSession loggedUser;
    private static final Logger logger = LoggerFactory.getLogger(RegisterController.class);

    private final UserDetailsService userDetailsService;
    private final ProfileImageService profileImageService;
    private final RoleService roleService;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, ModelMapper modelMapper, UserSession loggedUser, UserDetailsService userDetailsService, ProfileImageService profileImageService, RoleService roleService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.modelMapper = modelMapper;
        this.loggedUser = loggedUser;
        this.userDetailsService = userDetailsService;
        this.profileImageService = profileImageService;
        this.roleService = roleService;
    }

    @Override
    public   UserEntity  registerUser(UserRegisterDto userRegisterDto,
                                      Consumer<Authentication> successfulRegister) {
        UserEntity user = modelMapper.map(userRegisterDto, UserEntity.class);
        user.setPassword(passwordEncoder.encode(userRegisterDto.getPassword()));
        logger.debug("Encoded password: {}", user.getPassword());

       // user.getRoles().add(roleService.findByName(UserRoleEnum.USER));
        if (userRegisterDto.getImage() == null || Objects.equals(userRegisterDto.getImage().getOriginalFilename(), "")) {
            user.setImage(profileImageService.getDefaultProfileImage());

        }
        user.setImage(profileImageService.saveProfileImage(userRegisterDto.
                getImage(), user));


//                setFullName(userRegisterDto.getFullName()).
//             setAge(userRegisterDto.getAge()).
//                setEmail(userRegisterDto.getEmail()).
//                setUsername(userRegisterDto.getUsername()).
//                setPassword(passwordEncoder.encode(userRegisterDto.getPassword())
//                );

        userRepository.save(user);

        UserDetails userDetails =
                userDetailsService.loadUserByUsername(userRegisterDto.getUsername());

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                userDetails,
                userDetails.getPassword(),
                userDetails.getAuthorities()
        );

        successfulRegister.accept(authentication);
        return user;
    }

    @Override
    public UserViewModel getUserProfile() {
        UserEntity user = loggedUser.get();
        return modelMapper.map(user,UserViewModel.class);
    }

    @Override
    public UserViewModel findBId(Long id) {
        UserEntity user = loggedUser.get();
        user = userRepository.findById(id).orElseThrow(NoSuchElementException::new);
        return modelMapper.map(user,UserViewModel.class);
    }
}
