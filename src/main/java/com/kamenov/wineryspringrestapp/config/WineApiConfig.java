package com.kamenov.wineryspringrestapp.config;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.context.properties.bind.ConstructorBinding;

import org.springframework.context.annotation.Configuration;


@ConfigurationProperties(prefix = "wines.api")
@Configuration
public class WineApiConfig {


        private String baseUrl;

//    public WineApiConfig(String baseUrl) {
//        this.baseUrl = baseUrl;
//    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public WineApiConfig setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
        return this;
    }
}
