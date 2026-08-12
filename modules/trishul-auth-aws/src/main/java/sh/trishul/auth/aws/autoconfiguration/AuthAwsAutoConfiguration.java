package sh.trishul.auth.aws.autoconfiguration;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import sh.trishul.auth.aws.session.context.builder.CognitoPrincipalContextBuilder;
import sh.trishul.auth.session.context.PrincipalContextBuilder;

@Configuration
public class AuthAwsAutoConfiguration {

  @Bean
  @ConditionalOnMissingBean(PrincipalContextBuilder.class)
  public PrincipalContextBuilder principalContextBuilder() {
    return new CognitoPrincipalContextBuilder();
  }
}
