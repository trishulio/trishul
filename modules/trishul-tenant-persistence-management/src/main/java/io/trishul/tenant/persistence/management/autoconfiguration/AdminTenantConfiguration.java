package io.trishul.tenant.persistence.management.autoconfiguration;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.trishul.tenant.entity.Tenant;
import io.trishul.tenant.persistence.management.admin.AdminTenant;

@Configuration
public class AdminTenantConfiguration {
    @Bean
    @ConditionalOnMissingBean(Tenant.class)
    public Tenant adminTenant(@Value("${app.config.tenant.admin.id}") String id, @Value("${app.config.tenant.admin.name}") String name) {
        UUID adminId = UUID.fromString(id);

        return new AdminTenant(adminId, name);
    }
}
