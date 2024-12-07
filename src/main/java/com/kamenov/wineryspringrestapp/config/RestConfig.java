package com.kamenov.wineryspringrestapp.config;

import com.kamenov.wineryspringrestapp.service.JwtService;
import com.kamenov.wineryspringrestapp.service.UserService;
import org.apache.http.HttpHost;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.client.RestClient;
import java.util.Map;
import org.springframework.web.client.RestTemplate;

@Configuration
@ConfigurationPropertiesScan
public class RestConfig {
    @Bean
    public RestClient restClient() {
        return RestClient.create();
    }
    @Bean("winesRestClient")
    public RestClient winesRestClient(WineApiConfig wineApiConfig
    , ClientHttpRequestInterceptor requestInterceptor) {

        return RestClient.builder()
                .baseUrl(wineApiConfig.getBaseUrl())
                .defaultHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                .requestInterceptor(requestInterceptor)
                .build();

    }
    @Bean
    public ClientHttpRequestInterceptor requestInterceptor(UserService userService,
                                                           JwtService jwtService) {
        return (r, b, e) -> {
            // put the logged user details into bearer token
            userService
                    .getCurrentUser()
                    .ifPresent(mud -> {
                        String bearerToken = jwtService.generateTokenValue(
                                Map.of(
                                        "roles",
                                        mud.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList()
                                ),mud.getUuid().toString()
                        );

                        System.out.println("BEARER TOKEN: " + bearerToken);

                        r.getHeaders().setBearerAuth(bearerToken);
                    });

            return e.execute(r, b);
        };
    }
}
