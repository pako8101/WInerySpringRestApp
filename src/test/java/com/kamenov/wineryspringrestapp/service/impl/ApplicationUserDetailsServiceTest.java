package com.kamenov.wineryspringrestapp.service.impl;

import com.kamenov.wineryspringrestapp.models.user.AppUserDetails;
import com.kamenov.wineryspringrestapp.service.ApplicationUserDetailsService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public class ApplicationUserDetailsServiceTest {

    private ApplicationUserDetailsService toTest;

     static final String TEST_USERNAME = "pako";

     static final String TEST_PASSWORD = "pako";
    @BeforeEach
    void setUp() {
        toTest = new ApplicationUserDetailsService(new TestUserRepository());
    }

    @Test
    void loadUserByUsername_UserFound(){
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

}
