package com.kamenov.wineryspringrestapp.models.entity;

import com.kamenov.wineryspringrestapp.models.enums.UserRoleEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "roles")
public class UserRoleEntity extends BaseEntity {
    @NotNull
    @Column(unique = true)
    @Enumerated(EnumType.STRING)
    private UserRoleEnum name;

    public UserRoleEntity() {
    }

    public @NotNull UserRoleEnum getName() {
        return name;
    }

    public UserRoleEntity setName(@NotNull UserRoleEnum name) {
        this.name = name;
        return this;
    }
}
