package io.trishul.crud.autoconfiguration;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.trishul.crud.controller.filter.AttributeFilter;
import io.trishul.crud.service.LockService;
import io.trishul.model.executor.BlockingAsyncExecutor;

@Configuration
public class CrudAutoConfiguration {
  @Bean
  @ConditionalOnMissingBean(LockService.class)
  public LockService lockService() {
    return new LockService();
  }

  @Bean
  @ConditionalOnMissingBean(BlockingAsyncExecutor.class)
  public BlockingAsyncExecutor blockingAsyncExecutor() {
    return new BlockingAsyncExecutor();
  }

  @Bean
  @ConditionalOnMissingBean(AttributeFilter.class)
  public AttributeFilter attributeFilter() {
    return new AttributeFilter();
  }
}
