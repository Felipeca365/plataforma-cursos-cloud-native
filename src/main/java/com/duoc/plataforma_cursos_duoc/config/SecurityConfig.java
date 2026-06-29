package com.duoc.plataforma_cursos_duoc.config;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    private static final String CONSULTA_ROLE_CLAIM = "extension_consultaRole";

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(HttpMethod.GET, "/actuator/health").permitAll()

                // Cualquier usuario autenticado puede consultar cursos
                .requestMatchers(HttpMethod.GET, "/api/cursos").authenticated()

                // Solo usuarios con extension_consultaRole=profesor pueden usar S3
                .requestMatchers("/api/s3/**").hasRole("profesor")

                // El resto de endpoints requiere token válido
                .anyRequest().authenticated()
            )
            .oauth2ResourceServer(oauth2 -> oauth2
                .jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthenticationConverter()))
            );

        return http.build();
    }

    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
        converter.setJwtGrantedAuthoritiesConverter(this::extractAuthoritiesFromConsultaRole);
        return converter;
    }

    private Collection<GrantedAuthority> extractAuthoritiesFromConsultaRole(Jwt jwt) {
        Object claim = jwt.getClaim(CONSULTA_ROLE_CLAIM);

        if (claim instanceof String role && !role.isBlank()) {
            return List.of(new SimpleGrantedAuthority("ROLE_" + role.trim().toLowerCase()));
        }

        if (claim instanceof Collection<?> roles) {
            return roles.stream()
                .filter(String.class::isInstance)
                .map(String.class::cast)
                .filter(role -> !role.isBlank())
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.trim().toLowerCase()))
                .collect(Collectors.toList());
        }

        return List.of();
    }
}