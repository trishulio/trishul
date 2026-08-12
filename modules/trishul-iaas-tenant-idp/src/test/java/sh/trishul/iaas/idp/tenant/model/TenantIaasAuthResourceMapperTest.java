package sh.trishul.iaas.idp.tenant.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sh.trishul.iaas.access.role.model.IaasRole;

class TenantIaasAuthResourceMapperTest {
  private TenantIaasAuthResourceMapper mapper;

  @BeforeEach
  void init() {
    mapper = TenantIaasAuthResourceMapper.INSTANCE;
  }

  @Test
  void testFromComponents_ReturnsResourcesFromComponents() {
    List<IaasRole> roles = List.of(new IaasRole("ROLE_1"));

    List<TenantIaasAuthResources> resources = mapper.fromComponents(roles);

    List<TenantIaasAuthResources> expected
        = List.of(new TenantIaasAuthResources(new IaasRole("ROLE_1")));
    assertEquals(expected, resources);
  }

  @Test
  void testFromComponents_ReturnsNull_WhenRolesIsNull() {
    assertNull(mapper.fromComponents(null));
  }
}
