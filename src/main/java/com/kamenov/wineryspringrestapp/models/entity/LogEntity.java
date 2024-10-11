package com.kamenov.wineryspringrestapp.models.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.time.Instant;
import java.time.LocalDateTime;

@Entity
@Table(name = "logs")
public class LogEntity extends BaseEntity{

    @ManyToOne
    private UserEntity user;
    @ManyToOne
    private WineEntity wine;
    @Column(nullable = false,name = "action")
    private String action;
    @Column(nullable = false,name = "date_time")
    private LocalDateTime time;
    @Column(nullable = false)
    private Instant appearanceTime;

    public LogEntity() {
    }

    public UserEntity getUser() {
        return user;
    }

    public LogEntity setUser(UserEntity user) {
        this.user = user;
        return this;
    }

    public WineEntity getWine() {
        return wine;
    }

    public LogEntity setWine(WineEntity wine) {
        this.wine = wine;
        return this;
    }

    public String getAction() {
        return action;
    }

    public LogEntity setAction(String action) {
        this.action = action;
        return this;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public LogEntity setTime(LocalDateTime time) {
        this.time = time;
        return this;
    }

    public Instant getAppearanceTime() {
        return appearanceTime;
    }

    public LogEntity setAppearanceTime(Instant appearanceTime) {
        this.appearanceTime = appearanceTime;
        return this;
    }
}
