package sh.trishul.secrets.aws.autoconfiguration;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.amazonaws.services.secretsmanager.AWSSecretsManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sh.trishul.secrets.SecretsManager;
import sh.trishul.secrets.aws.SecretsAwsFactory;

class SecretsAwsAutoConfigurationTest {

  private SecretsAwsAutoConfiguration config;

  @BeforeEach
  void setUp() {
    config = new SecretsAwsAutoConfiguration();
  }

  @Test
  void testSecretsAwsFactory_ReturnsNonNull() {
    SecretsAwsFactory result = config.secretsAwsFactory();

    assertNotNull(result);
  }

  @Test
  void testSecretsManager_ReturnsNonNull() {
    SecretsAwsFactory mockFactory = mock(SecretsAwsFactory.class);
    AWSSecretsManager mockSecretsManager = mock(AWSSecretsManager.class);

    when(mockFactory.secretsManager("region", "url", "key", "secret"))
        .thenReturn(mockSecretsManager);

    SecretsManager<String, String> result
        = config.secretsManager(mockFactory, "region", "url", "key", "secret");

    assertNotNull(result);
  }
}
