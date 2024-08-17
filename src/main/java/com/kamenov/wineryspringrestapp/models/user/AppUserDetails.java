package com.kamenov.wineryspringrestapp.models.user;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class AppUserDetails extends User {

    private int age;
    private String fullName;

    public AppUserDetails(String username, String password,
                          Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }

    public int getAge() {
        return age;
    }

    public AppUserDetails setAge(int age) {
        this.age = age;
        return this;
    }

    public String getFullName() {
        return fullName;
    }

    public AppUserDetails setFullName(String fullName) {
        this.fullName = fullName;
        return this;
    }
}
