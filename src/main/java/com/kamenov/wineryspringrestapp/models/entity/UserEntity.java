package com.kamenov.wineryspringrestapp.models.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UuidGenerator;

import java.util.List;
import java.util.UUID;

import static java.sql.Types.VARCHAR;

@Entity
@Table(name = "user")
public class UserEntity extends BaseEntity {

    @Column(name = "full_name")
    private String fullName;
@UuidGenerator
   // @UUIDSequence
    @JdbcTypeCode(VARCHAR)
    private UUID uuid;

    @Column(name = "age")
    @NotNull
    private int age;
    @Column(nullable = false, unique = true)
    @NotNull
    private String email;

    @Column(unique = true, nullable = false)
    @NotNull
    private String username;

    @Column(nullable = false)
    @NotNull
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<UserRoleEntity> roles;

    @OneToOne(fetch = FetchType.EAGER)
    private ProfileImage image;

    private boolean RegistrationEmailSend;

    public UserEntity() {
    }

    public UUID getUuid() {
        return uuid;
    }

    public UserEntity setUuid(UUID uuid) {
        this.uuid = uuid;
        return this;
    }

    public boolean isRegistrationEmailSend() {
        return RegistrationEmailSend;
    }

    public UserEntity setRegistrationEmailSend(boolean registrationEmailSend) {
        RegistrationEmailSend = registrationEmailSend;
        return this;
    }

    public ProfileImage getImage() {
        return image;
    }

    public UserEntity setImage(ProfileImage image) {
        this.image = image;
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

    public String getFullName() {
        return fullName;
    }

    public UserEntity setFullName(String fullName) {
        this.fullName = fullName;
        return this;
    }

    @Override
    public String toString() {
        return "UserEntity{" +
                "fullName='" + fullName + '\'' +
                ", age=" + age +
                ", email='" + email + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", roles=" + roles +
                ", image=" + image +
                '}';
    }
}
