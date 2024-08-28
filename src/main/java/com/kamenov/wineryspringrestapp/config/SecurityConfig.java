package com.kamenov.wineryspringrestapp.config;

import com.kamenov.wineryspringrestapp.models.enums.UserRoleEnum;
import com.kamenov.wineryspringrestapp.repository.UserRepository;
import com.kamenov.wineryspringrestapp.service.ApplicationUserDetailsService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.context.DelegatingSecurityContextRepository;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.RequestAttributeSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;

@Configuration
public class SecurityConfig {

    private final String rememberMeKey;

    public SecurityConfig(@Value(
            "${remember.me.key}")String rememberMe){

        this.rememberMeKey = rememberMe;
    }



    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http,
                                           SecurityContextRepository securityContextRepository
         ,JwtAuthFilter jwtAuthFilter   ) throws Exception {

        http
                .authorizeHttpRequests(
                        authorizeHttpRequests ->
                                authorizeHttpRequests.
                                        requestMatchers(PathRequest.toStaticResources().atCommonLocations())
                                        .permitAll().
                                        requestMatchers("/",
                                                "/about",
                                                "/wines",
                                                "/wine",
                                                "/users/login-error",
                                                "/users/login", "/users/register"

                                        )
                                        .permitAll()
//                                        .anyRequest().authenticated().
                                        .requestMatchers("/error").permitAll().
                                        requestMatchers("/pages/admins").hasRole(UserRoleEnum.ADMIN.name()).
                                        requestMatchers("/pages/all").hasRole(UserRoleEnum.USER.name()).
                                        anyRequest().authenticated()


//                                       requestMatchers("/**").authenticated()
                )
                .formLogin(
                        (formLogin) ->
                                formLogin.
                                        loginPage("/users/login").
                                        usernameParameter(
                                                UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_USERNAME_KEY).
                                        passwordParameter(
                                                UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_PASSWORD_KEY).
                                        defaultSuccessUrl("/", true).
                                        failureForwardUrl("/users/login-error")

                )
                .logout((logout) ->
                        logout.logoutUrl("/users/logout").
                                logoutSuccessUrl("/").//go to homepage after logout
                                invalidateHttpSession(true)
                                .deleteCookies("JSESSIONID")


                )
                .rememberMe(httpSecurityRememberMeConfigurer -> {
                            httpSecurityRememberMeConfigurer.key(rememberMeKey)
                                    .rememberMeParameter("rememberMe")
                                    .rememberMeCookieName("rememberMe")
                                    .tokenValiditySeconds(10000);
                        }
                )

                .securityContext(
                        securityContext -> securityContext.
                                securityContextRepository(securityContextRepository)

                )
                .sessionManagement((session) -> session
                        .sessionCreationPolicy(SessionCreationPolicy.ALWAYS)
                )
                .addFilterBefore(jwtAuthFilter,UsernamePasswordAuthenticationFilter.class)
        ;

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService(UserRepository userRepository) {
        return new ApplicationUserDetailsService(userRepository);
    }

    @Bean
    public SecurityContextRepository securityContextRepository() {
        return new DelegatingSecurityContextRepository(
                new RequestAttributeSecurityContextRepository(),
                new HttpSessionSecurityContextRepository()
        );
    }

}
