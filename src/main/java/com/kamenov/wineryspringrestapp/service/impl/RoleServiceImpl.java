package com.kamenov.wineryspringrestapp.service.impl;

import com.kamenov.wineryspringrestapp.models.entity.UserRoleEntity;
import com.kamenov.wineryspringrestapp.models.enums.UserRoleEnum;
import com.kamenov.wineryspringrestapp.repository.RoleRepository;
import com.kamenov.wineryspringrestapp.repository.RoleRepository;
import com.kamenov.wineryspringrestapp.service.RoleService;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }


    @Override
    public UserRoleEntity findByName(UserRoleEnum name) {
        return roleRepository.findByName(name).orElse(null);
    }

    @Override
    public void saveAll(List<UserRoleEntity> roles) {
this.roleRepository.saveAll(roles);
    }

    @Override
    public List<UserRoleEntity> findAll() {
        return roleRepository.findAll();
    }
}
