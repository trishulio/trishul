package sh.trishul.auth.autoconfiguration;

import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
public class WebSecurityConfig {

  @Value("#{'${trishul.auth.cors.app-urls}'.split(';')}")
  private List<String> appUrls;

  public List<String> getAppUrls() {
    return this.appUrls == null ? null : new java.util.ArrayList<>(this.appUrls);
  }

  public void setAppUrls(List<String> appUrls) {
    this.appUrls = appUrls == null ? null : new java.util.ArrayList<>(appUrls);
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http.cors(Customizer.withDefaults())
        .authorizeHttpRequests(authorize -> authorize
            .requestMatchers("/actuator/**", "/public/**", "/static/**", "/api-docs/**",
                "/swagger-ui/**", "/swagger-resources/**", "/swagger-ui.html")
            .permitAll().anyRequest().authenticated())
        .oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()));

    return http.build();
  }

  @Bean
  public CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration configuration = new CorsConfiguration();
    if (this.appUrls != null && !this.appUrls.isEmpty()) {
      configuration.setAllowedOrigins(this.appUrls);
    }
    configuration.addAllowedMethod("*");
    configuration.addAllowedHeader("*");
    configuration.setAllowCredentials(true);

    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", configuration);
    return source;
  }
}
