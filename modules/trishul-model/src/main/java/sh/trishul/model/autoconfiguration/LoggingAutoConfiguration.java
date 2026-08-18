package sh.trishul.model.autoconfiguration;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import sh.trishul.model.aop.LoggingAspect;

/**
 * Auto-configuration that registers the {@link LoggingAspect} bean so that method-entry logging is
 * activated in any Spring Boot application that includes {@code trishul-model} on the classpath.
 *
 * <p>
 * The aspect can be replaced by providing an alternative {@link LoggingAspect} bean in the
 * application context.
 */
@Configuration
@EnableAspectJAutoProxy
public class LoggingAutoConfiguration {

  @Bean
  @ConditionalOnMissingBean(LoggingAspect.class)
  public LoggingAspect loggingAspect() {
    return new LoggingAspect();
  }
}
