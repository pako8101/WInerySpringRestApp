package com.kamenov.wineryspringrestapp.models.dto;


import com.kamenov.wineryspringrestapp.validation.passwordMatcher.PasswordMatcher;
import com.kamenov.wineryspringrestapp.validation.uniqueEmail.UniqueEmail;
import com.kamenov.wineryspringrestapp.validation.uniqueUsername.UniqueUsername;
import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.UniqueElements;
import org.springframework.web.multipart.MultipartFile;

@PasswordMatcher(
        password = "password",
        confirmPassword = "confirmPassword"
)
public class UserRegisterDto {

    private MultipartFile image;

    @Size(min = 3,max = 20,message = "Username must be between 3 and 20 symbols!")
    @NotNull(message = "Username must not be empty!")
    @UniqueUsername
    private String username;
    @NotEmpty
    @Size(min = 3,max = 20,message = "Full name must be between 3 and 20 symbols!")
    private String fullName;
    @Email(message = "Email must be valid format!")
    @NotBlank(message = "Email must not be empty!")
    @UniqueEmail
    private String email;
    @Size(min = 3,max = 20,message = "Password must be between 3 and 20 symbols!")
    @NotNull(message = "Password must not be empty!")
    private String password;
    @Size(min = 3,max = 20,message = "Password must be between 3 and 20 symbols!")
    @NotNull(message = "Password must not be empty!")
    private String confirmPassword;
    @Min(value = 18,message = "Over 18 years! ")
    @Max(120)
    @NotNull
    @Positive
    private int age;

    public UserRegisterDto() {
    }

    public MultipartFile getImage() {
        return image;
    }

    public UserRegisterDto setImage(MultipartFile image) {
        this.image = image;
        return this;
    }

    public @Size(min = 3, max = 20, message = "Username must be between 3 and 20 symbols!") @NotNull(message = "Username must not be empty!") String getUsername() {
        return username;
    }

    public UserRegisterDto setUsername(@Size(min = 3, max = 20, message = "Username must be between 3 and 20 symbols!") @NotNull(message = "Username must not be empty!") String username) {
        this.username = username;
        return this;
    }

    public @NotEmpty @Size(min = 3, max = 20, message = "Full name must be between 3 and 20 symbols!") String getFullName() {
        return fullName;
    }

    public UserRegisterDto setFullName(@NotEmpty @Size(min = 3, max = 20, message = "Full name must be between 3 and 20 symbols!") String fullName) {
        this.fullName = fullName;
        return this;
    }

    public @Email(message = "Email must be valid format!") @NotBlank(message = "Email must not be empty!") String getEmail() {
        return email;
    }

    public UserRegisterDto setEmail(@Email(message = "Email must be valid format!") @NotBlank(message = "Email must not be empty!") String email) {
        this.email = email;
        return this;
    }

    public @Size(min = 4, max = 20, message = "Password must be between 3 and 20 symbols!") @NotNull(message = "Password must not be empty!") String getPassword() {
        return password;
    }

    public UserRegisterDto setPassword(@Size(min = 4, max = 20, message = "Password must be between 3 and 20 symbols!") @NotNull(message = "Password must not be empty!") String password) {
        this.password = password;
        return this;
    }

    public @Size(min = 4, max = 20, message = "Password must be between 3 and 20 symbols!") @NotNull(message = "Password must not be empty!") String getConfirmPassword() {
        return confirmPassword;
    }

    public UserRegisterDto setConfirmPassword(@Size(min = 4, max = 20, message = "Password must be between 3 and 20 symbols!") @NotNull(message = "Password must not be empty!") String confirmPassword) {
        this.confirmPassword = confirmPassword;
        return this;
    }

    @Min(value = 18, message = "Over 18 years! ")
    @Max(120)
    @NotNull
    @Positive
    public int getAge() {
        return age;
    }

    public UserRegisterDto setAge(@Min(value = 18, message = "Over 18 years! ") @Max(120) @NotNull @Positive int age) {
        this.age = age;
        return this;
    }

    @Override
    public String toString() {
        return "UserRegisterDto{" +
                "image=" + image +
                ", username='" + username + '\'' +
                ", fullName='" + fullName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", confirmPassword='" + confirmPassword + '\'' +
                ", age=" + age +
                '}';
    }
}
