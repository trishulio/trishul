package io.trishul.iaas.access.aws;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import com.amazonaws.services.identitymanagement.model.Role;
import com.amazonaws.services.identitymanagement.model.RoleLastUsed;
import io.trishul.iaas.access.role.model.IaasRole;
import java.time.LocalDateTime;
import java.util.Date;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AwsIaasRoleMapperTest {
  private AwsIaasRoleMapper mapper;

  @BeforeEach
  public void init() {
    mapper = AwsIaasRoleMapper.INSTANCE;
  }

  @Test
  public void testFromIaasEntity_ReturnsNull_WhenArgIsNull() {
    assertNull(mapper.fromIaasEntity(null));
  }

  @Test
  public void testFromIaasEntity_ReturnsIaasEntity_WhenArgIsNotNull() {
    Role role = new Role().withArn("ROLE_1_ARN").withAssumeRolePolicyDocument("ROLE_1_DOC")
        .withCreateDate(new Date(1, 1, 1)).withDescription("ROLE_1_DESCRIPTION")
        .withRoleId("ROLE_1_ID").withRoleName("ROLE_1_NAME")
        .withRoleLastUsed(new RoleLastUsed().withLastUsedDate(new Date(2, 2, 2)));

    IaasRole entity = mapper.fromIaasEntity(role);

    IaasRole expected
        = new IaasRole("ROLE_1_NAME", "ROLE_1_DESCRIPTION", "ROLE_1_DOC", "ROLE_1_ARN", "ROLE_1_ID",
            LocalDateTime.of(1902, 3, 2, 0, 0), LocalDateTime.of(1901, 2, 1, 0, 0), null);

    assertEquals(expected, entity);
  }
}
