package com.kamenov.wineryspringrestapp.service.impl;

import com.kamenov.wineryspringrestapp.models.entity.UserEntity;
import com.kamenov.wineryspringrestapp.models.entity.UserRoleEntity;
import com.kamenov.wineryspringrestapp.models.enums.UserRoleEnum;
import com.kamenov.wineryspringrestapp.models.user.AppUserDetails;
import com.kamenov.wineryspringrestapp.repository.UserRepository;
import com.kamenov.wineryspringrestapp.service.ApplicationUserDetailsService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;
@ExtendWith(MockitoExtension.class)
public class ApplicationUserDetailsServiceTest {

    private ApplicationUserDetailsService toTest;

    private static final String TEST_USERNAME = "pako";
    private static final String TEST_NOT_EXIST_USERNAME = "PEPI";

    private static final String TEST_PASSWORD = "pako";
    @Mock
     private UserRepository mockUserRepo;
    @BeforeEach
    void setUp() {

        toTest = new ApplicationUserDetailsService(mockUserRepo);
    }

    @Test
    void loadUserByUsername_UserFound(){

        UserEntity testUSer =          new UserEntity()
                .setUsername(TEST_USERNAME)
                .setPassword(TEST_PASSWORD)
                .setFullName("pako")
                .setRoles(List.of(
                        new UserRoleEntity().setRole(UserRoleEnum.ADMIN),
                        new UserRoleEntity().setRole(UserRoleEnum.USER)
                ));

        when(mockUserRepo.findUserEntByUsername(TEST_USERNAME))
                .thenReturn(Optional.of(testUSer));

      UserDetails userDetails = toTest.loadUserByUsername(TEST_USERNAME);

      Assertions.assertInstanceOf( AppUserDetails.class, userDetails);

        AppUserDetails appUserDetails = (AppUserDetails) userDetails;

        Assertions.assertEquals(TEST_USERNAME, userDetails.getUsername());
        Assertions.assertEquals(TEST_PASSWORD, userDetails.getPassword());
       Assertions.assertEquals(testUSer.getFullName(), appUserDetails.getFullName());

     var expectedRoles  =   testUSer.getRoles().stream().map(UserRoleEntity::getRole).map(r -> "ROLE_" + r).toList();

     var actualRoles = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();


       Assertions.assertEquals(expectedRoles, actualRoles);

//        Optional<? extends GrantedAuthority> roleUser = userDetails.getAuthorities().stream()
//                .filter(a -> a.getAuthority().equals("ROLE_USER")).findAny();
//        Assertions.assertTrue(roleUser.isPresent());
//        Optional<? extends GrantedAuthority> roleAdmin = userDetails.getAuthorities().stream()
//                .filter(a -> a.getAuthority().equals("ROLE_ADMIN")).findAny();
//Assertions.assertTrue(roleAdmin.isPresent());

    }
    @Test
    void loadUserByUsername_UserNotFound(){
        Assertions.assertThrows(UsernameNotFoundException.class,
                () -> toTest.loadUserByUsername(TEST_NOT_EXIST_USERNAME));

    }

}
