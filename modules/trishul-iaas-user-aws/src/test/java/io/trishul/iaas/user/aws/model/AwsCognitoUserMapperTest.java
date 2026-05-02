package io.trishul.iaas.user.aws.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import com.amazonaws.services.cognitoidp.model.AttributeType;
import com.amazonaws.services.cognitoidp.model.UserType;
import io.trishul.auth.aws.session.context.CognitoPrincipalContext;
import io.trishul.iaas.user.model.IaasUser;
import java.time.LocalDateTime;
import java.util.Date;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AwsCognitoUserMapperTest {
  private AwsCognitoUserMapper mapper;

  @BeforeEach
  void init() {
    mapper = AwsCognitoUserMapper.INSTANCE;
  }

  @Test
  void testFromIaasEntity_ReturnsNull_WhenArgIsNull() {
    assertNull(mapper.fromIaasEntity(null));
  }

  @Test
  void testFromIaasEntity_ReturnsEntity_WhenArgIsNotNull() {
    UserType user = new UserType()
        .withAttributes(new AttributeType().withName(CognitoPrincipalContext.ATTRIBUTE_EMAIL)
            .withValue("EMAIL"))
        .withUserCreateDate(new Date(100, 0, 1)).withUserLastModifiedDate(new Date(101, 0, 1))
        .withUsername("USERNAME");

    IaasUser iaasUser = mapper.fromIaasEntity(user);

    IaasUser expected
        = new IaasUser().setEmail("EMAIL").setCreatedAt(LocalDateTime.of(2000, 1, 1, 0, 0))
            .setLastUpdated(LocalDateTime.of(2001, 1, 1, 0, 0));
    assertEquals(expected, iaasUser);
  }

  @Test
  void testFromIaasEntity_ReturnsEntityWithEmailSetFromUsername() {
    UserType user = new UserType().withUserCreateDate(new Date(100, 0, 1))
        .withUserLastModifiedDate(new Date(101, 0, 1)).withUsername("USERNAME");

    IaasUser iaasUser = mapper.fromIaasEntity(user);

    IaasUser expected
        = new IaasUser().setEmail("USERNAME").setCreatedAt(LocalDateTime.of(2000, 1, 1, 0, 0))
            .setLastUpdated(LocalDateTime.of(2001, 1, 1, 0, 0));
    assertEquals(expected, iaasUser);
  }
}
