package io.trishul.iaas.tenant.object.store;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import io.trishul.iaas.access.policy.model.IaasPolicy;
import io.trishul.object.store.model.IaasObjectStore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TenantIaasVfsResourcesTest {
  private TenantIaasVfsResources resources;

  @BeforeEach
  void init() {
    resources = new TenantIaasVfsResources();
  }

  @Test
  void testNoArgConstructor() {
    assertNull(resources.getObjectStore());
    assertNull(resources.getPolicy());
  }

  @Test
  void testAllArgConstructor() {
    resources
        = new TenantIaasVfsResources(new IaasObjectStore("OBJECT_STORE"), new IaasPolicy("POLICY"));

    assertEquals(new IaasObjectStore("OBJECT_STORE"), resources.getObjectStore());
    assertEquals(new IaasPolicy("POLICY"), resources.getPolicy());
  }

  @Test
  void testGetSetObjectStore() {
    resources.setObjectStore(new IaasObjectStore("OBJECT_STORE"));

    assertEquals(new IaasObjectStore("OBJECT_STORE"), resources.getObjectStore());
  }

  @Test
  void testGetSetPolicy() {
    resources.setPolicy(new IaasPolicy("POLICY"));

    assertEquals(new IaasPolicy("POLICY"), resources.getPolicy());
  }
}
