package com.kamenov.wineryspringrestapp.service.impl;

import com.kamenov.wineryspringrestapp.models.entity.UserEntity;
import com.kamenov.wineryspringrestapp.models.entity.UserRoleEntity;
import com.kamenov.wineryspringrestapp.models.enums.UserRoleEnum;
import com.kamenov.wineryspringrestapp.repository.UserRepository;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

import static com.kamenov.wineryspringrestapp.service.impl.ApplicationUserDetailsServiceTest.TEST_PASSWORD;
import static com.kamenov.wineryspringrestapp.service.impl.ApplicationUserDetailsServiceTest.TEST_USERNAME;
@Repository
public class TestUserRepository implements UserRepository {
    @Override
    public Optional<UserEntity> findUserEntByUsername(String username) {
        if (Objects.equals(username, TEST_USERNAME)) {

            return Optional.of(
                    new UserEntity()
                            .setUsername(TEST_USERNAME)
                            .setPassword(TEST_PASSWORD)
                            .setFullName("pako")
                            .setRoles(List.of(
                                    new UserRoleEntity().setRole(UserRoleEnum.USER),
                                    new UserRoleEntity().setRole(UserRoleEnum.ADMIN)
                            ))
            );


        }
        return Optional.empty();
    }

    @Override
    public Optional<UserEntity> findByEmail(String value) {
        return Optional.empty();
    }

    @Override
    public List<UserEntity> findUsersPendingEmails() {
        return List.of();
    }

    @Override
    public void flush() {

    }

    @Override
    public <S extends UserEntity> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public <S extends UserEntity> List<S> saveAllAndFlush(Iterable<S> entities) {
        return List.of();
    }

    @Override
    public void deleteAllInBatch(Iterable<UserEntity> entities) {

    }

    @Override
    public void deleteAllByIdInBatch(Iterable<Long> longs) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public UserEntity getOne(Long aLong) {
        return null;
    }

    @Override
    public UserEntity getById(Long aLong) {
        return null;
    }

    @Override
    public UserEntity getReferenceById(Long aLong) {
        return null;
    }

    @Override
    public <S extends UserEntity> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends UserEntity> List<S> findAll(Example<S> example) {
        return List.of();
    }

    @Override
    public <S extends UserEntity> List<S> findAll(Example<S> example, Sort sort) {
        return List.of();
    }

    @Override
    public <S extends UserEntity> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends UserEntity> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends UserEntity> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends UserEntity, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }

    @Override
    public <S extends UserEntity> S save(S entity) {
        return null;
    }

    @Override
    public <S extends UserEntity> List<S> saveAll(Iterable<S> entities) {
        return List.of();
    }

    @Override
    public Optional<UserEntity> findById(Long aLong) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(Long aLong) {
        return false;
    }

    @Override
    public List<UserEntity> findAll() {
        return List.of();
    }

    @Override
    public List<UserEntity> findAllById(Iterable<Long> longs) {
        return List.of();
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(Long aLong) {

    }

    @Override
    public void delete(UserEntity entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends Long> longs) {

    }

    @Override
    public void deleteAll(Iterable<? extends UserEntity> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public List<UserEntity> findAll(Sort sort) {
        return List.of();
    }

    @Override
    public Page<UserEntity> findAll(Pageable pageable) {
        return null;
    }
}
