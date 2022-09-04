package org.almondiz.almondiz.config.security;

import lombok.RequiredArgsConstructor;
import org.almondiz.almondiz.user.AuthService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final CorsConfig corsConfig;

    private final AuthService authService;

    @Bean
    public WebSecurityCustomizer configure() {
        return (web) -> web.ignoring()
                           .antMatchers("/swagger-ui/index.html", "/h2-console", "/h2-console/*")
                           .antMatchers("/api/user/signin", "/api/user/signup", "/api/users", "/api/user/*", "/api/user");
    }

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
            .anyRequest().permitAll()
            .and()
            .exceptionHandling().accessDeniedHandler(new CAccessDeniedHandler())
            .and()
            .exceptionHandling().authenticationEntryPoint(new CAuthenticationEntryPoint())
            .and()
            .addFilterBefore(new JwtAuthenticationFilter(authService), UsernamePasswordAuthenticationFilter.class);
        http.headers().frameOptions().disable();
        return http.build();
    }



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
