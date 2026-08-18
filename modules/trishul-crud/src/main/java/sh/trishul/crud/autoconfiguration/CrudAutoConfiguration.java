package sh.trishul.crud.autoconfiguration;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import sh.trishul.crud.controller.filter.AttributeFilter;
import sh.trishul.crud.service.LockService;
import sh.trishul.model.executor.BlockingAsyncExecutor;

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
