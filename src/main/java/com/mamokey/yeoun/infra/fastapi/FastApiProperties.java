package com.mamokey.yeoun.infra.fastapi;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "fastapi")
public record FastApiProperties(String baseUrl) {
}
