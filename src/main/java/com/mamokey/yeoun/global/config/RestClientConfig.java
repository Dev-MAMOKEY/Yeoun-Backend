package com.mamokey.yeoun.global.config;

import com.mamokey.yeoun.infra.fastapi.FastApiProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
@RequiredArgsConstructor
public class RestClientConfig {

    private final FastApiProperties fastApiProperties;

    @Bean
    public RestClient restClient() {
        return RestClient.builder()
                .baseUrl(fastApiProperties.baseUrl())
                .build();
    }
}
