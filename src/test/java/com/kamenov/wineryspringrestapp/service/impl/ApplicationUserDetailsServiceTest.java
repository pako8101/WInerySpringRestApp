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
import org.mockito.Mockito;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

public class ApplicationUserDetailsServiceTest {

    private ApplicationUserDetailsService toTest;

     static final String TEST_USERNAME = "pako";
     static final String TEST_NOT_EXIST_USERNAME = "PEPI";

     static final String TEST_PASSWORD = "pako";
     private UserRepository mockUserRepo;
    @BeforeEach
    void setUp() {
     mockUserRepo =   Mockito.mock(UserRepository.class);
        toTest = new ApplicationUserDetailsService(mockUserRepo);
    }

    @Test
    void loadUserByUsername_UserFound(){

        UserEntity testUSer =          new UserEntity()
                .setUsername(TEST_USERNAME)
                .setPassword(TEST_PASSWORD)
                .setFullName("pako")
                .setRoles(List.of(
                        new UserRoleEntity().setRole(UserRoleEnum.USER),
                        new UserRoleEntity().setRole(UserRoleEnum.ADMIN)
                ));

        when(mockUserRepo.findUserEntByUsername(TEST_USERNAME))
                .thenReturn(Optional.of(testUSer));

      UserDetails userDetails = toTest.loadUserByUsername(TEST_USERNAME);

      Assertions.assertInstanceOf( AppUserDetails.class, userDetails);

        AppUserDetails appUserDetails = (AppUserDetails) userDetails;

        Assertions.assertEquals(TEST_USERNAME, userDetails.getUsername());
        Assertions.assertEquals(TEST_PASSWORD, userDetails.getPassword());
       Assertions.assertEquals("pako", appUserDetails.getFullName());

       Assertions.assertEquals(2, appUserDetails.getAuthorities().size());

        Optional<? extends GrantedAuthority> roleUser = userDetails.getAuthorities().stream()
                .filter(a -> a.getAuthority().equals("ROLE_USER")).findAny();
        Assertions.assertTrue(roleUser.isPresent());
        Optional<? extends GrantedAuthority> roleAdmin = userDetails.getAuthorities().stream()
                .filter(a -> a.getAuthority().equals("ROLE_ADMIN")).findAny();
Assertions.assertTrue(roleAdmin.isPresent());

    }
    @Test
    void loadUserByUsername_UserNotFound(){
        Assertions.assertThrows(UsernameNotFoundException.class,
                () -> toTest.loadUserByUsername(TEST_NOT_EXIST_USERNAME));

    }

}
