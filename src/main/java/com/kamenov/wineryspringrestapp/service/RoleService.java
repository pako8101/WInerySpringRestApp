package com.kamenov.wineryspringrestapp.service;

import com.kamenov.wineryspringrestapp.models.entity.UserRoleEntity;
import com.kamenov.wineryspringrestapp.models.enums.UserRoleEnum;

import java.util.List;

public interface RoleService {
    UserRoleEntity findByName(UserRoleEnum name);

    void saveAll(List<UserRoleEntity> roles);

    List<UserRoleEntity> findAll();
}
