package com.kamenov.wineryspringrestapp.service.impl;

import com.kamenov.wineryspringrestapp.models.dto.UserRegisterDto;
import com.kamenov.wineryspringrestapp.models.entity.UserEntity;
import com.kamenov.wineryspringrestapp.models.user.UserSession;
import com.kamenov.wineryspringrestapp.models.view.UserViewModel;
import com.kamenov.wineryspringrestapp.repository.UserRepository;
import com.kamenov.wineryspringrestapp.service.ApplicationUserDetailsService;
import com.kamenov.wineryspringrestapp.service.ProfileImageService;
import com.kamenov.wineryspringrestapp.service.RoleService;
import com.kamenov.wineryspringrestapp.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.function.Consumer;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private UserDetailsService userDetailsService;


    @Mock
    private ProfileImageService profileImageService;

    @Mock
    private RoleService roleService;

    @Mock
    private UserSession loggedUser;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void registerUser_ShouldRegisterUserSuccessfully() {
        UserRegisterDto userRegisterDto = new UserRegisterDto();
        userRegisterDto.setUsername("testuser");
        userRegisterDto.setPassword("password123");
        userRegisterDto.setFullName("Test User");
        userRegisterDto.setEmail("test@example.com");

        UserEntity userEntity = new UserEntity();
        userEntity.setUsername("testuser");

        when(modelMapper.map(userRegisterDto, UserEntity.class)).thenReturn(userEntity);
        when(passwordEncoder.encode(userRegisterDto.getPassword())).thenReturn("encodedPassword");
        when(userRepository.save(any(UserEntity.class))).thenReturn(userEntity);

        UserDetails userDetails = Mockito.mock(UserDetails.class);
        Mockito.lenient().
                when(userDetailsService.loadUserByUsername(userRegisterDto.getUsername())).thenReturn(userDetails);



        Consumer<Authentication> successfulRegister = mock(Consumer.class);

        UserEntity registeredUser = userService.registerUser(userRegisterDto, successfulRegister);

        assertNotNull(registeredUser);
        verify(passwordEncoder).encode(userRegisterDto.getPassword());
        verify(userRepository).save(any(UserEntity.class));
        verify(successfulRegister).accept(any(Authentication.class));
    }

    @Test
    void getUserProfile_ShouldReturnUserProfile() {
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername("testuser");
        UserViewModel userViewModel = new UserViewModel();
        userViewModel.setUsername("testuser");

        when(loggedUser.get()).thenReturn(userEntity);
        when(modelMapper.map(userEntity, UserViewModel.class)).thenReturn(userViewModel);

        UserViewModel userProfile = userService.getUserProfile();

        assertNotNull(userProfile);
        assertEquals("testuser", userProfile.getUsername());
        verify(loggedUser).get();
    }

    @Test
    void findBId_ShouldReturnUserViewModelIfUserExists() {
        Long userId = 1L;
        UserEntity userEntity = new UserEntity();
        userEntity.setId(userId);
        UserViewModel userViewModel = new UserViewModel();

        when(userRepository.findById(userId)).thenReturn(Optional.of(userEntity));
        when(modelMapper.map(userEntity, UserViewModel.class)).thenReturn(userViewModel);

        UserViewModel foundUser = userService.findBId(userId);

        assertNotNull(foundUser);
        verify(userRepository).findById(userId);
        verify(modelMapper).map(userEntity, UserViewModel.class);
    }

    @Test
    void findByName_ShouldReturnUserEntityIfUserExists() {
        String username = "testuser";
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(username);

        when(userRepository.findUserEntByUsername(username)).thenReturn(Optional.of(userEntity));

        UserEntity foundUser = userService.findByName(username);

        assertNotNull(foundUser);
        assertEquals(username, foundUser.getUsername());
        verify(userRepository).findUserEntByUsername(username);
    }

    @Test
    void findByName_ShouldThrowExceptionIfUserNotFound() {
        String username = "unknownuser";

        when(userRepository.findUserEntByUsername(username)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> userService.findByName(username));
    }

}
