package io.trishul.iaas.tenant.object.store;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
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
    TenantIaasVfsResources result = resources.setObjectStore(new IaasObjectStore("OBJECT_STORE"));

    assertEquals(new IaasObjectStore("OBJECT_STORE"), resources.getObjectStore());
    assertEquals(resources, result);
  }

  @Test
  void testSetObjectStore_Null() {
    resources.setObjectStore(new IaasObjectStore("OBJECT_STORE"));
    resources.setObjectStore(null);

    assertNull(resources.getObjectStore());
  }

  @Test
  void testGetSetPolicy() {
    TenantIaasVfsResources result = resources.setPolicy(new IaasPolicy("POLICY"));

    assertEquals(new IaasPolicy("POLICY"), resources.getPolicy());
    assertEquals(resources, result);
  }

  @Test
  void testSetPolicy_Null() {
    resources.setPolicy(new IaasPolicy("POLICY"));
    resources.setPolicy(null);

    assertNull(resources.getPolicy());
  }

  @Test
  void testEqualsHashCode() {
    TenantIaasVfsResources resources1
        = new TenantIaasVfsResources(new IaasObjectStore("OS1"), new IaasPolicy("P1"));
    TenantIaasVfsResources resources2
        = new TenantIaasVfsResources(new IaasObjectStore("OS1"), new IaasPolicy("P1"));
    TenantIaasVfsResources resources3
        = new TenantIaasVfsResources(new IaasObjectStore("OS2"), new IaasPolicy("P2"));

    assertEquals(resources1, resources2);
    assertEquals(resources1.hashCode(), resources2.hashCode());
    assertNotEquals(resources1, resources3);
    assertNotEquals(resources1.hashCode(), resources3.hashCode());
  }

  @Test
  void testToString() {
    TenantIaasVfsResources resources
        = new TenantIaasVfsResources(new IaasObjectStore("OS1"), new IaasPolicy("P1"));
    String toString = resources.toString();

    assertTrue(toString.contains("OS1"));
    assertTrue(toString.contains("P1"));
  }
}
