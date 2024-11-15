package com.kamenov.wineryspringrestapp.web;

import com.kamenov.wineryspringrestapp.models.entity.UserEntity;
import com.kamenov.wineryspringrestapp.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
public class RegistrationControllerIT {

@Autowired
    private MockMvc mockMvc;
@Autowired
private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;


    @Test
    void testRegistration() throws Exception {
    mockMvc.perform(post("/users/subscribe")
            .param("email", "pako@email.com")
            .param("password", "pako")
            .param("fullName", "pako")
                    .with(csrf())
    ).andExpect(status().is3xxRedirection())
            .andExpect(redirectedUrl("/"));
    Optional<UserEntity> optionalUser = userRepository.findByEmail("pako@email.com");

    Assertions.assertTrue(optionalUser.isPresent());

    UserEntity user = optionalUser.get();
    Assertions.assertEquals("pako@email.com", user.getEmail());
    Assertions.assertTrue(passwordEncoder.matches("pako", user.getPassword()));
    Assertions.assertEquals("pako", user.getFullName());


}




}
