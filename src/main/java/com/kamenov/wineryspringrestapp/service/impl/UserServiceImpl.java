package com.kamenov.wineryspringrestapp.service.impl;

import com.kamenov.wineryspringrestapp.models.dto.UserRegisterDto;
import com.kamenov.wineryspringrestapp.models.entity.UserEntity;
import com.kamenov.wineryspringrestapp.models.enums.UserRoleEnum;
import com.kamenov.wineryspringrestapp.repository.UserRepository;
import com.kamenov.wineryspringrestapp.service.ProfileImageService;
import com.kamenov.wineryspringrestapp.service.RoleService;
import com.kamenov.wineryspringrestapp.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.function.Consumer;
@Service
public class UserServiceImpl implements UserService {
private final UserRepository userRepository;
private final PasswordEncoder passwordEncoder;
private final ModelMapper modelMapper;
    private final UserDetailsService userDetailsService;
    private final ProfileImageService profileImageService;
    private final RoleService roleService;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, ModelMapper modelMapper, UserDetailsService userDetailsService, ProfileImageService profileImageService, RoleService roleService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.modelMapper = modelMapper;
        this.userDetailsService = userDetailsService;
        this.profileImageService = profileImageService;
        this.roleService = roleService;
    }

    @Override
    public   void  registerUser(UserRegisterDto userRegisterDto,
                                Consumer<Authentication> successfulRegister) {
        UserEntity user = modelMapper.map(userRegisterDto, UserEntity.class);
        user.setPassword(passwordEncoder.encode(userRegisterDto.getPassword()));
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
    }
}
