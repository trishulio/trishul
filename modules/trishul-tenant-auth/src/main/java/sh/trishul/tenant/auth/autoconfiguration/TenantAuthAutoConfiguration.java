package sh.trishul.tenant.auth.autoconfiguration;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import sh.trishul.auth.session.context.holder.ContextHolder;
import sh.trishul.tenant.auth.model.ContextHolderTenantIdProvider;
import sh.trishul.tenant.entity.AdminTenant;
import sh.trishul.tenant.entity.TenantIdProvider;

@Configuration
public class TenantAuthAutoConfiguration {
  @Bean
  @ConditionalOnMissingBean(TenantIdProvider.class)
  public TenantIdProvider tenantIdProvider(ContextHolder contextHolder, AdminTenant adminTenant) {
    return new ContextHolderTenantIdProvider(contextHolder, adminTenant);
  }
}
