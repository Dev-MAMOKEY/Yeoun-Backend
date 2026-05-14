package com.mamokey.yeoun.infra.fastapi;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "custom.fastapi")
public record FastApiProperties(String baseUrl, String internalToken) {}
