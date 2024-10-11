package com.kamenov.wineryspringrestapp.models.service;

import java.time.Instant;
import java.time.LocalDateTime;

public class LogServiceModel {

    private Long id;
    private String user;
    private String wine;
    private String action;
    private LocalDateTime time;
    private Instant appearanceTime;
    public LogServiceModel() {}

    public String getUser() {
        return user;
    }

    public LogServiceModel setUser(String user) {
        this.user = user;
        return this;
    }

    public Long getId() {
        return id;
    }

    public LogServiceModel setId(Long id) {
        this.id = id;
        return this;
    }

    public String getWine() {
        return wine;
    }

    public LogServiceModel setWine(String wine) {
        this.wine = wine;
        return this;
    }

    public String getAction() {
        return action;
    }

    public LogServiceModel setAction(String action) {
        this.action = action;
        return this;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public LogServiceModel setTime(LocalDateTime time) {
        this.time = time;
        return this;
    }

    public Instant getAppearanceTime() {
        return appearanceTime;
    }

    public LogServiceModel setAppearanceTime(Instant appearanceTime) {
        this.appearanceTime = appearanceTime;
        return this;
    }
}
