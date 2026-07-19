package com.duoc.bff.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestClientConfig {

    // Un solo RestTemplate compartido para hablar con ms-cursos,
    // ms-inscripciones y ms-storage (todos internos, no publicos).
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
