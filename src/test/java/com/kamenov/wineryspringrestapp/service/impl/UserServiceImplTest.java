package com.kamenov.wineryspringrestapp.service.impl;

import com.kamenov.wineryspringrestapp.models.dto.UserRegisterDto;
import com.kamenov.wineryspringrestapp.models.entity.UserEntity;
import com.kamenov.wineryspringrestapp.models.user.UserSession;
import com.kamenov.wineryspringrestapp.models.view.UserViewModel;
import com.kamenov.wineryspringrestapp.repository.RoleRepository;
import com.kamenov.wineryspringrestapp.repository.UserRepository;
import com.kamenov.wineryspringrestapp.service.ProfileImageService;
import com.kamenov.wineryspringrestapp.service.RoleService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
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
    private UserRepository mockUserRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private UserDetailsService userDetailsService;


    @Mock
    private ProfileImageService profileImageService;
@Mock
   private UserDetails userDetails;
    @Captor
    private ArgumentCaptor<UserEntity> userEntityArgumentCaptor
            = ArgumentCaptor.forClass(UserEntity.class);

    @Mock
    private UserSession loggedUser;
    @Mock
    private RoleService mockRoleService;

   // @InjectMocks
    private UserServiceImpl toTest;

    @BeforeEach
    void setUp() {
        toTest = new UserServiceImpl(
                mockUserRepository,
                passwordEncoder,
                new ModelMapper(),
                loggedUser,userDetailsService,profileImageService,mockRoleService);

        MockitoAnnotations.openMocks(this);
    }

    @Test
    void registerUser_ShouldRegisterUserSuccessfully() {
        //arrange
        UserRegisterDto userRegisterDto =
         new UserRegisterDto().
        setUsername("testuser").
        setPassword("password123").
       setFullName("Test User").
       setEmail("test@example.com");

        UserEntity userEntity = new UserEntity();
        userEntity.setUsername("testuser");

        when(modelMapper.map(userRegisterDto, UserEntity.class)).thenReturn(userEntity);
        when(passwordEncoder.encode(userRegisterDto.getPassword())).thenReturn("encodedPassword");
        when(mockUserRepository.save(any(UserEntity.class))).thenReturn(userEntity);

        //UserDetails userDetails = Mockito.mock(UserDetails.class);
        Mockito.lenient().
                when(userDetailsService.loadUserByUsername(userRegisterDto.getUsername())).thenReturn(userDetails);


//act
       Consumer<Authentication> successfulRegister = mock(Consumer.class);
        toTest.registerUser(userRegisterDto,successfulRegister);
        //assert
        verify(mockUserRepository,times(2))
                .save(userEntityArgumentCaptor.capture());
      UserEntity actualEntity =   userEntityArgumentCaptor.getValue();

        Assertions.assertEquals(userRegisterDto.getUsername(),
                actualEntity.getUsername());
        Assertions.assertEquals(userRegisterDto.getFullName(),
                actualEntity.getFullName());
        Assertions.assertEquals(userRegisterDto.getPassword(),
                actualEntity.getPassword());


//
//        UserEntity registeredUser = userService.registerUser(userRegisterDto, successfulRegister);

//        assertNotNull(registeredUser);
//        verify(passwordEncoder).encode(userRegisterDto.getPassword());
//        verify(userRepository).save(any(UserEntity.class));
//        verify(successfulRegister).accept(any(Authentication.class));
    }

    @Test
    void getUserProfile_ShouldReturnUserProfile() {
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername("testuser");
        UserViewModel userViewModel = new UserViewModel();
        userViewModel.setUsername("testuser");

        when(loggedUser.get()).thenReturn(userEntity);
        when(modelMapper.map(userEntity, UserViewModel.class)).thenReturn(userViewModel);

        UserViewModel userProfile = toTest.getUserProfile();

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

        when(mockUserRepository.findById(userId)).thenReturn(Optional.of(userEntity));
        when(modelMapper.map(userEntity, UserViewModel.class)).thenReturn(userViewModel);

        UserViewModel foundUser = toTest.findBId(userId);

        assertNotNull(foundUser);
        verify(mockUserRepository).findById(userId);
        verify(modelMapper).map(userEntity, UserViewModel.class);
    }

    @Test
    void findByName_ShouldReturnUserEntityIfUserExists() {
        String username = "testuser";
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(username);

        when(mockUserRepository.findUserEntByUsername(username)).thenReturn(Optional.of(userEntity));

        UserEntity foundUser = toTest.findByName(username);

        assertNotNull(foundUser);
        assertEquals(username, foundUser.getUsername());
        verify(mockUserRepository).findUserEntByUsername(username);
    }

    @Test
    void findByName_ShouldThrowExceptionIfUserNotFound() {
        String username = "unknownuser";

        when(mockUserRepository.findUserEntByUsername(username)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> toTest.findByName(username));
    }

}
