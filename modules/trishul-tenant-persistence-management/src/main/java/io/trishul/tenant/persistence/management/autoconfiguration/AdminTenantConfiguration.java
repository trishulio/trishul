package io.trishul.tenant.persistence.management.autoconfiguration;

import java.net.MalformedURLException;
import java.net.URL;
import io.trishul.tenant.entity.AdminTenant;
import io.trishul.tenant.entity.Tenant;
import io.trishul.tenant.entity.TenantData;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AdminTenantConfiguration {
  @Bean
  @ConditionalOnMissingBean(Tenant.class)
  public TenantData adminTenant(@Value("${app.config.tenant.admin.id}") String id,
      @Value("${app.config.tenant.admin.name}") String name) throws MalformedURLException {
    UUID adminId = UUID.fromString(id);

    return new AdminTenant(adminId, name, new URL("http://localhost/"));
  }
}
