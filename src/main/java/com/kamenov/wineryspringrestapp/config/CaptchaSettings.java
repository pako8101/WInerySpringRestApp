package com.kamenov.wineryspringrestapp.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "google.recaptcha")
public class CaptchaSettings {
    @Value("${site_key}")
    private String site;
    @Value("${secret_key}")
    private String secret;

    public CaptchaSettings() {
    }

    public String getSite() {
        return site;
    }

    public CaptchaSettings setSite(String site) {
        this.site = site;
        return this;
    }

    public String getSecret() {
        return secret;
    }

    public CaptchaSettings setSecret(String secret) {
        this.secret = secret;
        return this;
    }
}
