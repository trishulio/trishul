package io.trishul.iaas.tenant.object.store;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import io.trishul.iaas.access.policy.model.IaasPolicy;
import io.trishul.object.store.model.IaasObjectStore;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TenantIaasVfsResourceMapperTest {
  private TenantIaasVfsResourceMapper mapper;

  @BeforeEach
  public void init() {
    mapper = TenantIaasVfsResourceMapper.INSTANCE;
  }

  @Test
  public void testFromComponents_ReturnsEntities_WhenListSizeIsSame() {
    List<IaasObjectStore> objectStores
        = List.of(new IaasObjectStore("OS_1"), new IaasObjectStore("OS_2"));
    List<IaasPolicy> policies = List.of(new IaasPolicy("P1"), new IaasPolicy("P2"));

    List<TenantIaasVfsResources> resources = mapper.fromComponents(objectStores, policies);

    List<TenantIaasVfsResources> expected
        = List.of(new TenantIaasVfsResources(new IaasObjectStore("OS_1"), new IaasPolicy("P1")),
            new TenantIaasVfsResources(new IaasObjectStore("OS_2"), new IaasPolicy("P2")));
    assertEquals(expected, resources);
  }

  @Test
  public void testFromComponents_ThrowsException_WhenDifferentListLengths() {
    List<IaasObjectStore> objectStores
        = List.of(new IaasObjectStore("OS_1"), new IaasObjectStore("OS_2"));
    List<IaasPolicy> policies = List.of(new IaasPolicy("P1"), new IaasPolicy("P2"));

    List<TenantIaasVfsResources> resources = mapper.fromComponents(objectStores, policies);

    List<TenantIaasVfsResources> expected
        = List.of(new TenantIaasVfsResources(new IaasObjectStore("OS_1"), new IaasPolicy("P1")),
            new TenantIaasVfsResources(new IaasObjectStore("OS_2"), new IaasPolicy("P2")));
    assertEquals(expected, resources);
  }

  @Test
  public void testFromComponents_ThrowsException_WhenComponentsAreNotSameSize() {
    IllegalArgumentException exception1 = assertThrows(IllegalArgumentException.class,
        () -> mapper.fromComponents(List.of(), List.of(new IaasPolicy())));
    IllegalArgumentException exception2 = assertThrows(IllegalArgumentException.class,
        () -> mapper.fromComponents(List.of(new IaasObjectStore()), List.of()));
  }
}
