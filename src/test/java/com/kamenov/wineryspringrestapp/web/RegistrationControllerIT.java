package com.kamenov.wineryspringrestapp.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
public class RegistrationControllerIT {

@Autowired
    private MockMvc mockMvc;

@Test
    void testRegistration() throws Exception {
    mockMvc.perform(post("/users/subscribe")
            .param("email", "pako@email.com")
            .param("password", "pako")
            .param("fullName", "pako")
                    .with(csrf())
    ).andExpect(status().is3xxRedirection())
            .andExpect(redirectedUrl("/"));
}

}
