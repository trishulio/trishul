package io.trishul.tenant.auth.autoconfiguration;

import io.trishul.auth.session.context.holder.ContextHolder;
import io.trishul.tenant.auth.model.ContextHolderTenantIdProvider;
import io.trishul.tenant.entity.AdminTenant;
import io.trishul.tenant.entity.TenantIdProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TenantAuthAutoConfiguration {
  @Bean
  @ConditionalOnMissingBean(TenantIdProvider.class)
  public TenantIdProvider tenantIdProvider(ContextHolder contextHolder, AdminTenant adminTenant) {
    return new ContextHolderTenantIdProvider(contextHolder, adminTenant);
  }
}
