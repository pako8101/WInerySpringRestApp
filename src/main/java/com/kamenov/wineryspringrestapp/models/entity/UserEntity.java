package com.kamenov.wineryspringrestapp.models.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "user")
public class UserEntity extends BaseEntity {
    @Column(nullable = false,name = "first_name")
    private String firstName;
    @Column(nullable = false,name = "last_name")
    private String lastName;
    @Column(name = "age")
    private int age;
    @Column(nullable = false,unique = true)
    private String email;

    @Column(unique = true,nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<UserRoleEntity> roles ;

    public UserEntity() {
    }
    public String getFullName() {
        return firstName + " " + lastName;
    }



    public String getFirstName() {
        return firstName;
    }

    public UserEntity setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public UserEntity setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public int getAge() {
        return age;
    }

    public UserEntity setAge(int age) {
        this.age = age;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public UserEntity setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public UserEntity setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public UserEntity setPassword(String password) {
        this.password = password;
        return this;
    }

    public List<UserRoleEntity> getRoles() {
        return roles;
    }

    public UserEntity setRoles(List<UserRoleEntity> roles) {
        this.roles = roles;
        return this;
    }
}
