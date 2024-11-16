package io.trishul.auth.aws.autoconfiguration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.trishul.auth.aws.session.context.builder.CognitoPrincipalContextBuilder;
import io.trishul.auth.session.context.PrincipalContextBuilder;

@Configuration
public class AwsAutoConfiguration {

    @Bean
    public PrincipalContextBuilder ctxBuilder() {
        return new CognitoPrincipalContextBuilder();
    }
}
