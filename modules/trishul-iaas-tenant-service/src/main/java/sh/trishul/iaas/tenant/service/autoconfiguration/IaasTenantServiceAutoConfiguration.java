package sh.trishul.iaas.tenant.service.autoconfiguration;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import sh.trishul.iaas.idp.tenant.model.TenantIaasIdpTenantMapper;
import sh.trishul.iaas.tenant.idp.management.service.TenantIaasAuthService;
import sh.trishul.iaas.tenant.idp.management.service.TenantIaasIdpService;
import sh.trishul.iaas.tenant.object.store.service.service.TenantIaasVfsService;
import sh.trishul.iaas.tenant.service.TenantIaasService;

@Configuration
public class IaasTenantServiceAutoConfiguration {
  @Bean
  @ConditionalOnMissingBean(TenantIaasService.class)
  public TenantIaasService tenantIaasService(TenantIaasAuthService authService,
      TenantIaasIdpService idpService, TenantIaasVfsService vfsService) {
    return new TenantIaasService(authService, idpService, vfsService,
        TenantIaasIdpTenantMapper.INSTANCE);
  }

}
