package com.kstoi.social.config;

import com.kstoi.social.config.auth.JwtAuthenticationProvider;
import com.kstoi.social.config.auth.JwtFilter;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@AllArgsConstructor
@EnableWebSecurity
public class SecurityConfig {
    private JwtAuthenticationProvider jwtAuthenticationProvider;
    private AuthenticationManager authenticationManager;
    private JwtFilter authenticationFilter;
    @Bean
    @Order(1)
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf( c -> c.disable());
        http.sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http.authorizeHttpRequests( c ->{
            c.requestMatchers("/user/login","/user/register").permitAll()
                    .anyRequest().authenticated();
        });
        http.formLogin( c->{
            c.disable();

        });
        http.logout(c -> c.permitAll());
        http.authenticationProvider(jwtAuthenticationProvider);
        http.authenticationManager(authenticationManager);
        SecurityContextHolder.setStrategyName(SecurityContextHolder.MODE_THREADLOCAL);
        http.addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
