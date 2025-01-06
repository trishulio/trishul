package io.trishul.secrets.aws.autoconfiguration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.services.secretsmanager.AWSSecretsManager;

import io.trishul.secrets.SecretsManager;
import io.trishul.secrets.aws.AwsSecretsManager;
import io.trishul.secrets.aws.SecretsAwsFactory;

@Configuration
public class SecretsAwsAutoConfiguration {
    @Bean
    @ConditionalOnMissingBean (SecretsAwsFactory.class)
    public SecretsAwsFactory secretsAwsFactory () {
        return new SecretsAwsFactory();
    }

    @Bean
    @ConditionalOnMissingBean (AWSSecretsManager.class)
    public SecretsManager<String, String>  secretsManager(SecretsAwsFactory secretsAwsFactory, @Value("${aws.secretsmanager.region}") String region, @Value("${aws.secretsmanager.url}") String url, @Value("${aws.secretsmanager.access-key}") String secretsManagerKey, @Value("${aws.secretsmanager.access-secret-key}") String secretsManagerSecretKey) {
        AWSSecretsManager client = secretsAwsFactory.secretsManager(region, url, secretsManagerKey, secretsManagerSecretKey);
        return new AwsSecretsManager(client);
    }
}
