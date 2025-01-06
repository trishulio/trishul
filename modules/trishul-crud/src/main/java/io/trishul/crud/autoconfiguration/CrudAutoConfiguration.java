package io.trishul.crud.autoconfiguration;

import io.trishul.crud.service.LockService;
import io.trishul.model.executor.BlockingAsyncExecutor;
import io.trishul.model.util.ThreadLocalUtilityProvider;
import io.trishul.model.validator.UtilityProvider;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CrudAutoConfiguration {
  @Bean
  public LockService lockService() {
    return new LockService();
  }

  @Bean
  @ConditionalOnMissingBean(UtilityProvider.class)
  public UtilityProvider utilityProvider() {
    return new ThreadLocalUtilityProvider();
  }

  @Bean
  @ConditionalOnMissingBean(BlockingAsyncExecutor.class)
  public BlockingAsyncExecutor executor() {
    return new BlockingAsyncExecutor();
  }
}
