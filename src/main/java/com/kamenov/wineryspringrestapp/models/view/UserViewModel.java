package com.kamenov.wineryspringrestapp.models.view;

public class UserViewModel {
    private Long id;
    private String fullName;
    private String username;
    private int age;

    public UserViewModel() {
    }

    public Long getId() {
        return id;
    }

    public UserViewModel setId(Long id) {
        this.id = id;
        return this;
    }

    public String getFullName() {
        return fullName;
    }

    public UserViewModel setFullName(String fullName) {
        this.fullName = fullName;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public UserViewModel setUsername(String username) {
        this.username = username;
        return this;
    }

    public int getAge() {
        return age;
    }

    public UserViewModel setAge(int age) {
        this.age = age;
        return this;
    }
}
