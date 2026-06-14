package io.trishul.iaas.user.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class IaasUserTenantMembershipTest {
  private IaasUserTenantMembership membership;

  @BeforeEach
  void init() {
    membership = new IaasUserTenantMembership();
  }

  @Test
  void testNoArgConstructor() {
    assertNull(membership.getId());
    assertNull(membership.getUser());
    assertNull(membership.getTenantId());
    assertNull(membership.getVersion());
  }

  @Test
  void testIdConstructor() {
    membership = new IaasUserTenantMembership(new IaasUserTenantMembershipId("USER", "TENANT"));

    assertEquals(new IaasUserTenantMembershipId("USER", "TENANT"), membership.getId());
  }

  @Test
  void testAllArgConstructor() {
    membership = new IaasUserTenantMembership(new IaasUser("USER"), "T1");

    assertEquals(new IaasUser("USER"), membership.getUser());
    assertEquals("T1", membership.getTenantId());
  }

  @Test
  void testGetSetId() {
    membership.setId(new IaasUserTenantMembershipId("USER", "TENANT"));
    assertEquals(new IaasUserTenantMembershipId("USER", "TENANT"), membership.getId());
  }

  @Test
  void testGetSetUser() {
    membership.setUser(new IaasUser("USER"));

    assertEquals(new IaasUser("USER"), membership.getUser());
  }

  @Test
  void testGetSetTenantId() {
    membership.setTenantId("T1");

    assertEquals("T1", membership.getTenantId());
  }

  @Test
  void testSetId_Null() {
    membership.setId(null);
    assertNull(membership.getId());
  }

  @Test
  void testSetId_ReusesExistingUser() {
    IaasUser user = new IaasUser("EXISTING");
    membership.setUser(user);
    membership.setId(new IaasUserTenantMembershipId("NEW_USER", "NEW_TENANT"));
    assertEquals("EXISTING", membership.getUser().getEmail());
    assertEquals("NEW_TENANT", membership.getTenantId());
  }

  @Test
  void testSetVersion() {
    membership.setVersion(1);
    assertNull(membership.getVersion());
  }
}
