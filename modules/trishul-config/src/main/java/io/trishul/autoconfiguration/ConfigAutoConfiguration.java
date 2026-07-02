package io.trishul.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConfigAutoConfiguration {
  @Bean
  @ConditionalOnMissingBean(AppConfig.class)
  public AppConfig appConfig(@Value("${app.name}") String appName) {
    return new AppConfig(appName);
  }
}
