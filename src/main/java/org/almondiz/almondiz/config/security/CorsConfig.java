package org.almondiz.almondiz.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();

        config.setAllowCredentials(true);
        config.addAllowedOriginPattern("*");
        config.addAllowedOrigin("http://localhost:8080");
        config.addAllowedHeader("*");
        config.addExposedHeader("*");
        config.addAllowedMethod("*");
        config.addExposedHeader("AUTH-TOKEN");
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}
