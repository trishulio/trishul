package io.trishul.auth.autoconfiguration;

import javax.servlet.Filter;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;

import io.trishul.auth.session.context.holder.ContextHolder;
import io.trishul.auth.session.context.holder.ThreadLocalContextHolder;
import io.trishul.auth.session.filters.ContextHolderFilter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
            .antMatchers("/actuator/**", "/public/**", "/static/**", "/api-docs/**", "/swagger-ui/**", "/swagger-resources/**", "/swagger-ui.html")
            .permitAll()
            .anyRequest().authenticated()
            .and()
            .oauth2ResourceServer()
            .jwt();
    }

    @Bean
    @ConditionalOnMissingBean(JwtDecoder.class)
    public JwtDecoder decoder(OAuth2ResourceServerProperties props) {
        String jwkUri = props.getJwt().getJwkSetUri();
        return NimbusJwtDecoder.withJwkSetUri(jwkUri).build();
    }

    @Bean
    @ConditionalOnMissingBean(ContextHolderFilter.class)
    public Filter ctxHolderFilter(ContextHolder ctxHolder) {
        return new ContextHolderFilter((ThreadLocalContextHolder) ctxHolder);
    }


}
