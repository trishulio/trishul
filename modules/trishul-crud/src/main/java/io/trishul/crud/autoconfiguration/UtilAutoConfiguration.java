package io.trishul.crud.autoconfiguration;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.trishul.model.executor.BlockingAsyncExecutor;
import io.trishul.model.util.ThreadLocalUtilityProvider;
import io.trishul.model.util.UtilityProvider;

@Configuration
public class UtilAutoConfiguration {
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
