package com.project.securityservice.config;

import com.project.securityservice.filter.JwtAuthenticationFilter;
import com.project.securityservice.util.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@ComponentScan(basePackages = "com.project.securityservice")
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.csrf(AbstractHttpConfigurer::disable);
        http.httpBasic(AbstractHttpConfigurer::disable);
        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        http
                .authorizeHttpRequests(auth -> auth
                        // CUSTOMER 기능은 모든 권한에 가능
                        .requestMatchers("/customer/**")
                        .hasAnyAuthority("CUSTOMER", "PARTNER")
                        // PARTNER 가입은 일반회원만 가능
                        .requestMatchers("/partner/*/enroll").hasAuthority("CUSTOMER")
                        // PARTNER 기능은 PARTNER만 가능
                        .requestMatchers("/partner/**").hasAuthority("PARTNER")
                        .anyRequest().permitAll()
                );

        http
                .addFilterBefore(
                        jwtAuthenticationFilter,
                        UsernamePasswordAuthenticationFilter.class
                );

        return http.build();
    }
}
