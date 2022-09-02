package org.almondiz.almondiz.config.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final CorsConfig corsConfig;

    // 수정 필요
    @Bean
    public WebSecurityCustomizer configure() {
        return (web) -> web.ignoring()
            .antMatchers("/swagger-ui/index.html", "/h2-console", "/h2-console/*")
            .antMatchers("/api/user/signin", "/api/user/signup", "/api/users", "/api/user/*", "/api/user/*/posts", "/api/user", "/api/post/*", "/api/post", "/api/posts", "api/store/*");
    }

    // 수정 필요
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf().disable()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .formLogin().disable()
            .httpBasic().disable()
            .apply(new customDsl())
            .and()
            .authorizeRequests()
            .antMatchers("/api/user/**").access("hasRole('ROLE_USER')")
            .anyRequest().permitAll();
        http.headers().frameOptions().disable();
        return http.build();
    }

    ;

    public class customDsl extends AbstractHttpConfigurer<customDsl, HttpSecurity> {

        @Override
        public void configure(HttpSecurity http) {
            AuthenticationManager authenticationManager = http.getSharedObject(
                AuthenticationManager.class);
            http
                .addFilter(corsConfig.corsFilter());
        }

    }
}
