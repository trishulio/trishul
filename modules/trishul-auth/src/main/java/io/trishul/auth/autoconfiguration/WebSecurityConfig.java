package io.trishul.auth.autoconfiguration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class WebSecurityConfig {
  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http.authorizeHttpRequests(authorize -> authorize
        .requestMatchers("/actuator/**", "/public/**", "/static/**", "/api-docs/**",
            "/swagger-ui/**", "/swagger-resources/**", "/swagger-ui.html")
        .permitAll().anyRequest().authenticated()).oauth2ResourceServer(oauth2 -> oauth2.jwt());

    return http.build();
  }
}
