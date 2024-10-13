package io.trishul.auth.autoconfiguration;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.trishul.auth.session.context.holder.ContextHolder;
import io.trishul.auth.session.context.holder.ThreadLocalContextHolder;

@Configuration
public class AuthConfiguration {
    @Bean
    @ConditionalOnMissingBean(ContextHolder.class)
    public ContextHolder ctxHolder() {
        return new ThreadLocalContextHolder();
    }
}
