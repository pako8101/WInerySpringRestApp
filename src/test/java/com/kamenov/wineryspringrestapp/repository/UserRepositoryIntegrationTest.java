package com.kamenov.wineryspringrestapp.repository;

import com.kamenov.wineryspringrestapp.models.entity.UserEntity;
import jakarta.persistence.EntityManager;
import org.hibernate.validator.internal.constraintvalidators.bv.AssertTrueValidator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.core.userdetails.User;

import java.util.List;

@DataJpaTest
public class UserRepositoryIntegrationTest {
    @Autowired
    private EntityManager entityManager;
    @Autowired
    private UserRepository userRepository;

@Test
    public void testFindById(){
        UserEntity user = new UserEntity();
        user.setUsername("pako");
        user.setPassword("pako");
        user.setEmail("pako");
        user.setFullName("pako");
        user.setAge(24);
        user.setRoles(List.of());
        entityManager.flush();

        entityManager.persist(user);
        UserEntity found = userRepository.findById(user.getId()).orElse(null);
        Assertions.assertEquals(found.getUsername(), user.getUsername());
    }


}
