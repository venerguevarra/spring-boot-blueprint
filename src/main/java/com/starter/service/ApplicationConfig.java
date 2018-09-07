package com.starter.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurerSupport;

import lombok.Getter;
import lombok.Setter;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "application-config")
@Getter
@Setter
public class ApplicationConfig extends AsyncConfigurerSupport {
    private String version;
    private String mode;
    private Boolean wiremock;
    private Integer wiremockPort;
    private List<String> apiKeys = new ArrayList<>();
}
