package sh.trishul.iaas.auth.autoconfiguration;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import sh.trishul.iaas.auth.session.context.ContextHolderAuthorizationFetcher;
import sh.trishul.iaas.auth.session.context.IaasAuthorizationCredentialsBuilder;
import sh.trishul.iaas.auth.session.context.IaasAuthorizationFetcher;
import sh.trishul.iaas.auth.session.context.holder.IaasAuthorizationCredentialsHolder;
import sh.trishul.iaas.auth.session.context.holder.ThreadLocalIaasAuthorizationCredentialsHolder;
import sh.trishul.iaas.auth.session.filters.IaasAuthorizationCredentialsHolderFilter;

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

  @Bean
  @ConditionalOnMissingBean(ContextHolderAuthorizationFetcher.class)
  public ContextHolderAuthorizationFetcher contextHolderAuthorizationFetcher(
      IaasAuthorizationFetcher fetcher,
      IaasAuthorizationCredentialsHolder iaasAuthorizationCredentialsHolder) {
    return new ContextHolderAuthorizationFetcher(fetcher, iaasAuthorizationCredentialsHolder);
  }
}
