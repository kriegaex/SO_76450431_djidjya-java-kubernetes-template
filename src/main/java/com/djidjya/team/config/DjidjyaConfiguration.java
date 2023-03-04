package com.djidjya.team.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.autoconfigure.metrics.MeterRegistryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.DefaultOAuth2AuthenticatedPrincipal;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.security.oauth2.server.resource.introspection.NimbusOpaqueTokenIntrospector;
import org.springframework.security.oauth2.server.resource.introspection.OpaqueTokenIntrospector;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Configuration
public class DjidjyaConfiguration {

    private static final String[] AUTH_WHITELIST = {
            "/v3/api-docs"
    };

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        return mapper;
    }

    @Bean
    public CorsConfiguration corsConfiguration() {
        var configuration = new CorsConfiguration().applyPermitDefaultValues();
        configuration.setAllowedOriginPatterns(List.of("*"));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"));
        return configuration;
    }

    @Bean
    public OpaqueTokenIntrospector introspector(@Value("${spring.security.oauth2.resourceserver.opaquetoken.introspection-uri}") String introUri,
                                                @Value("${spring.security.oauth2.resourceserver.opaquetoken.client-id}") String clientId,
                                                @Value("${spring.security.oauth2.resourceserver.opaquetoken.client-secret}") String clientSecret) {
        OpaqueTokenIntrospector delegate = new NimbusOpaqueTokenIntrospector(introUri, clientId, clientSecret);
        return token -> {
            OAuth2AuthenticatedPrincipal principal = delegate.introspect(token);
            Map<String, Object> realmAccess = principal.getAttribute("realm_access");
            List<String> roles = (List<String>) realmAccess.get("roles");
            Collection<GrantedAuthority> mapped = roles.stream()
                    .map(rn -> new SimpleGrantedAuthority("ROLE_" + rn))
                    .collect(Collectors.toList());
            return new DefaultOAuth2AuthenticatedPrincipal(
                    principal.getName(), principal.getAttributes(), mapped);
        };
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity,
                                                   CorsConfiguration corsConfiguration) throws Exception {
        return httpSecurity.cors()
                .configurationSource(request -> corsConfiguration)
                .and()
                .csrf()
                .disable()
                .authorizeHttpRequests()
                .requestMatchers(AUTH_WHITELIST).permitAll()
                .requestMatchers(HttpMethod.POST, "/users/unverified").permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .oauth2ResourceServer(OAuth2ResourceServerConfigurer::opaqueToken)
                .build();
    }

    @Bean
    MeterRegistryCustomizer<MeterRegistry> configurer(
            @Value("${spring.application.name}") String applicationName) {
        return (registry) -> registry.config().commonTags("application", applicationName);
    }

}
