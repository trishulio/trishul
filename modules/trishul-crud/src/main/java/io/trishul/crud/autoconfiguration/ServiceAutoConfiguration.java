package io.trishul.crud.autoconfiguration;

import io.trishul.crud.service.LockService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceAutoConfiguration {
    @Bean
    public LockService lockService() {
        return new LockService();
    }
}
