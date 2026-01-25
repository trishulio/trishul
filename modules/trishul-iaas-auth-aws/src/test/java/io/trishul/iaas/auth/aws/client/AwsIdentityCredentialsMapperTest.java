package io.trishul.iaas.auth.aws.client;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import com.amazonaws.services.cognitoidentity.model.Credentials;
import io.trishul.iaas.auth.session.context.IaasAuthorization;
import java.time.LocalDateTime;
import java.util.Date;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AwsIdentityCredentialsMapperTest {
  private AwsIdentityCredentialsMapper mapper;

  @BeforeEach
  void init() {
    mapper = AwsIdentityCredentialsMapper.INSTANCE;
  }

  @Test
  void testFromIaasEntity_ReturnsNull_WhenArgIsNull() {
    assertNull(mapper.fromIaasEntity(null));
  }

  @Test
  void testFromIaasEntity_ReturnsEntity_WhenIaasEntityIsNull() {
    Credentials creds
        = new Credentials().withAccessKeyId("ACCESS_KEY_ID").withSecretKey("SECRET_KEY")
            .withSessionToken("SESSION_TOKEN").withExpiration(new Date(1, 1, 1));

    IaasAuthorization auth = mapper.fromIaasEntity(creds);

    IaasAuthorization expired = new IaasAuthorization("ACCESS_KEY_ID", "SECRET_KEY",
        "SESSION_TOKEN", LocalDateTime.of(1901, 2, 1, 0, 0));
    assertEquals(expired, auth);
  }
}
