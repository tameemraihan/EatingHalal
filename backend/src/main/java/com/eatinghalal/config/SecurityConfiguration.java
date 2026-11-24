package com.eatinghalal.config;

import com.eatinghalal.repository.UserAccountRepository;
import org.springframework.context.annotation.*;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;

@Configuration
public class SecurityConfiguration {
  @Bean
  PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  UserDetailsService userDetailsService(UserAccountRepository users) {
    return username ->
        users
            .findByUsername(username)
            .map(
                account ->
                    User.withUsername(account.username)
                        .password(account.passwordHash)
                        .roles(account.role)
                        .build())
            .orElseThrow(() -> new UsernameNotFoundException("Unknown account"));
  }

  @Bean
  SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http.csrf(csrf -> csrf.csrfTokenRepository(new HttpSessionCsrfTokenRepository()))
        .authorizeHttpRequests(
            auth ->
                auth.requestMatchers(
                        "/api/session",
                        "/api/products",
                        "/api/products/**",
                        "/api/photos",
                        "/",
                        "/index.html",
                        "/assets/**",
                        "/favicon.ico",
                        "/error")
                    .permitAll()
                    .requestMatchers("/api/admin/**")
                    .hasRole("ADMIN")
                    .anyRequest()
                    .authenticated())
        .formLogin(
            login ->
                login
                    .loginProcessingUrl("/api/login")
                    .successHandler((request, response, auth) -> response.setStatus(204))
                    .failureHandler((request, response, exception) -> response.setStatus(401)))
        .logout(
            logout ->
                logout
                    .logoutUrl("/api/logout")
                    .logoutSuccessHandler((request, response, auth) -> response.setStatus(204)))
        .exceptionHandling(
            errors ->
                errors
                    .authenticationEntryPoint(
                        (request, response, exception) -> response.sendError(401))
                    .accessDeniedHandler(
                        (request, response, exception) -> response.sendError(403)));
    return http.build();
  }
}
