package io.trishul.crud.autoconfiguration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.trishul.crud.service.LockService;

@Configuration
public class ServiceAutoConfiguration {
    @Bean
    public LockService lockService() {
        return new LockService();
    }
}
