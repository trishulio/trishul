package io.trishul.auth.autoconfiguration;

import io.trishul.auth.session.context.PrincipalContextBuilder;
import io.trishul.auth.session.context.holder.ContextHolder;
import io.trishul.auth.session.context.holder.ThreadLocalContextHolder;
import io.trishul.auth.session.filters.ContextHolderFilter;
import jakarta.servlet.Filter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;

@Configuration
public class AuthAutoConfiguration {
  @Bean
  @ConditionalOnMissingBean(ContextHolder.class)
  public ContextHolder contextHolder() {
    return new ThreadLocalContextHolder();
  }

  @Bean
  @ConditionalOnMissingBean(ContextHolderFilter.class)
  public Filter contextHolderFilter(ContextHolder contextHolder, PrincipalContextBuilder principalContextBuilder) {
    return new ContextHolderFilter((ThreadLocalContextHolder) contextHolder, principalContextBuilder);
  }

  @Bean
  @ConditionalOnMissingBean(JwtDecoder.class)
  public JwtDecoder jwtDecoder(OAuth2ResourceServerProperties props) {
    String jwkUri = props.getJwt().getJwkSetUri();
    return NimbusJwtDecoder.withJwkSetUri(jwkUri).build();
  }
}
