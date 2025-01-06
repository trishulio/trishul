package io.trishul.iaas.auth.autoconfiguration;

import io.trishul.iaas.auth.session.context.IaasAuthorizationCredentialsBuilder;
import io.trishul.iaas.auth.session.context.holder.IaasAuthorizationCredentialsHolder;
import io.trishul.iaas.auth.session.context.holder.ThreadLocalIaasAuthorizationCredentialsHolder;
import io.trishul.iaas.auth.session.filters.IaasAuthorizationCredentialsHolderFilter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class IaasAuthAutoConfiguration {

  @Bean
  @ConditionalOnMissingBean(IaasAuthorizationCredentialsHolder.class)
  public IaasAuthorizationCredentialsHolder iaasAuthorizationCredentialsHolder() {
    return new ThreadLocalIaasAuthorizationCredentialsHolder();
  }

  @Bean
  @ConditionalOnMissingBean(IaasAuthorizationCredentialsBuilder.class)
  public IaasAuthorizationCredentialsBuilder iaasAuthorizationCredentialsBuilder() {
    return new IaasAuthorizationCredentialsBuilder();
  }

  @Bean
  @ConditionalOnMissingBean(IaasAuthorizationCredentialsHolderFilter.class)
  public IaasAuthorizationCredentialsHolderFilter iaasAuthorizationCredentialsHolderFilter(
      IaasAuthorizationCredentialsHolder iaasAuthorizationCredentialsHolder,
      IaasAuthorizationCredentialsBuilder iaasAuthorizationCredentialsBuilder) {
    return new IaasAuthorizationCredentialsHolderFilter(
        (ThreadLocalIaasAuthorizationCredentialsHolder) iaasAuthorizationCredentialsHolder,
        iaasAuthorizationCredentialsBuilder);
  }
}
