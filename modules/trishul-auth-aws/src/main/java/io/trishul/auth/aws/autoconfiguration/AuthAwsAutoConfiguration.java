package io.trishul.auth.aws.autoconfiguration;

import io.trishul.auth.aws.session.context.builder.CognitoPrincipalContextBuilder;
import io.trishul.auth.session.context.PrincipalContextBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AuthAwsAutoConfiguration {

  @Bean
  public PrincipalContextBuilder ctxBuilder() {
    return new CognitoPrincipalContextBuilder();
  }
}
