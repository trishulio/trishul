package io.trishul.tenant.auth.autoconfiguration;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.trishul.auth.session.context.holder.ContextHolder;
import io.trishul.tenant.auth.TenantIdProvider;
import io.trishul.tenant.auth.model.ContextHolderTenantIdProvider;

@Configuration
public class AuthAutoConfiguration {
    @Bean
    @ConditionalOnMissingBean(TenantIdProvider.class)
    public TenantIdProvider tenantIdProvider(ContextHolder ctxHolder) {
        return new ContextHolderTenantIdProvider(ctxHolder);
    }
}
