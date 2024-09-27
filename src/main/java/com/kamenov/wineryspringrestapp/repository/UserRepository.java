package com.kamenov.wineryspringrestapp.repository;

import com.kamenov.wineryspringrestapp.models.entity.UserEntity;
import com.kamenov.wineryspringrestapp.service.ApplicationUserDetailsService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findUserEntByUsername(String username);
    Optional<UserEntity> findByEmail(String value);
@Query("select u from  UserEntity u where u.RegistrationEmailSend = false")
    List<UserEntity> findUsersPendingEmails();
}
