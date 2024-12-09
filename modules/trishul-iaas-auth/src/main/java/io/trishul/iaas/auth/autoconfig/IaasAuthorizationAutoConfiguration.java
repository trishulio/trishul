package io.trishul.iaas.auth.autoconfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.trishul.iaas.auth.session.context.IaasAuthorizationCredentialsBuilder;
import io.trishul.iaas.auth.session.context.holder.IaasAuthorizationCredentialsHolder;
import io.trishul.iaas.auth.session.context.holder.ThreadLocalIaasAuthorizationCredentialsHolder;
import io.trishul.iaas.auth.session.filters.IaasAuthorizationCredentialsHolderFilter;

@Configuration
public class IaasAuthorizationAutoConfiguration {

    @Bean
    public IaasAuthorizationCredentialsHolder ctxHolder() {
        return new ThreadLocalIaasAuthorizationCredentialsHolder();
    }

    @Bean
    public IaasAuthorizationCredentialsBuilder credentialsBuilder() {
        return new IaasAuthorizationCredentialsBuilder();
    }

    @Bean
    public IaasAuthorizationCredentialsHolderFilter iaasAuthorizationCredentialsHolderFilter(IaasAuthorizationCredentialsHolder ctxHolder, IaasAuthorizationCredentialsBuilder credentialsBuilder) {
        return new IaasAuthorizationCredentialsHolderFilter((ThreadLocalIaasAuthorizationCredentialsHolder) ctxHolder, credentialsBuilder);
    }
}
