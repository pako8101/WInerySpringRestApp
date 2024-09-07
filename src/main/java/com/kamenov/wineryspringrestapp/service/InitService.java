package com.kamenov.wineryspringrestapp.service;

import com.kamenov.wineryspringrestapp.models.entity.UserEntity;
import com.kamenov.wineryspringrestapp.models.entity.UserRoleEntity;
import com.kamenov.wineryspringrestapp.models.enums.UserRoleEnum;
import com.kamenov.wineryspringrestapp.repository.RoleRepository;
import com.kamenov.wineryspringrestapp.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InitService {
    private final RoleRepository userRoleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final String defaultPassword;


    public InitService(RoleRepository userRoleRepository,
                       UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       @Value("pako") String defaultPassword) {
        this.userRoleRepository = userRoleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;


        this.defaultPassword = defaultPassword;
    }

    @PostConstruct
    public void init() {
        initRoles();
        initUsers();
    }

    private void initRoles() {
        if (userRoleRepository.count() == 0) {

            var adminRole = new UserRoleEntity().setRole(UserRoleEnum.ADMIN);
            var userRole = new UserRoleEntity().setRole(UserRoleEnum.USER);

            userRoleRepository.save(adminRole);
            userRoleRepository.save(userRole);
        }
    }

    private void initUsers() {
        if (userRepository.count() == 0) {
            initAdmin();
            initNormalUser();
        }
    }

    private void initAdmin(){


        var adminUser = new UserEntity().
                setEmail("admin@example.com").
                setFullName("Admin").
                setAge(33).
                setUsername("admin").
                setPassword(passwordEncoder.encode(defaultPassword)).
                setRoles(userRoleRepository.findAll());

        userRepository.save(adminUser);
    }

//    private void initModerator(){
//
//        var moderatorRole = userRoleRepository.
//                findUserRoleEntityByRole(UserRoleEnum.MODERATOR).orElseThrow();
//
//        var moderatorUser = new UserEnt().
//                setEmail("moderator@example.com").
//                setFullName("Moderator").
//                setUsername("moderator").
//                setPassword(passwordEncoder.encode(defaultPassword)).
//                setRoles(List.of(moderatorRole));
//
//        userRepository.save(moderatorUser);
//    }

    private void initNormalUser(){
        var userRole = userRoleRepository.
                findUserRoleEntityByRole(UserRoleEnum.USER).orElseThrow();

        var normalUser = new UserEntity().
                setEmail("user@example.com").
                setFullName("User").
                setAge(22)
                .setUsername("user").
                setPassword(passwordEncoder.encode(defaultPassword))
                .setRoles(List.of(userRole));

        userRepository.save(normalUser);
    }
}
