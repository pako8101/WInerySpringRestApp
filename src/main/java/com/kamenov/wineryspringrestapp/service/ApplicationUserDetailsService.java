package com.kamenov.wineryspringrestapp.service;


import com.kamenov.wineryspringrestapp.models.entity.UserEntity;
import com.kamenov.wineryspringrestapp.models.entity.UserRoleEntity;
import com.kamenov.wineryspringrestapp.models.user.AppUserDetails;
import com.kamenov.wineryspringrestapp.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

public class ApplicationUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public ApplicationUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public AppUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return
                userRepository.
                        findUserEntByUsername(username).
                        map(ApplicationUserDetailsService::map).
                        orElseThrow(() -> new UsernameNotFoundException("User with name " + username + " not found!"));
    }

    private static   AppUserDetails map(UserEntity userEntity) {

        return new AppUserDetails(
                userEntity.getUsername(),
                userEntity.getPassword(),
                extractAuthorities(userEntity),userEntity.getUuid()
        ).setAge(userEntity.getAge()).
                setFullName(userEntity.getFullName());

    }


    private static List<GrantedAuthority> extractAuthorities(UserEntity userEntity) {
        return userEntity.
                getRoles().
                stream().
                map(ApplicationUserDetailsService::mapRole).
                toList();
    }

    private static GrantedAuthority mapRole(UserRoleEntity userRoleEntity) {
        return new SimpleGrantedAuthority("ROLE_" + userRoleEntity.getRole().name());

    }
}
