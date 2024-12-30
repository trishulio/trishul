package io.trishul.iaas.user.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class IaasUserTenantMembershipTest {
  private IaasUserTenantMembership membership;

  @BeforeEach
  public void init() {
    membership = new IaasUserTenantMembership();
  }

  @Test
  public void testNoArgConstructor() {
    assertNull(membership.getId());
    assertNull(membership.getUser());
    assertNull(membership.getTenantId());
    assertNull(membership.getVersion());
  }

  @Test
  public void testIdConstructor() {
    membership = new IaasUserTenantMembership(new IaasUserTenantMembershipId("USER", "TENANT"));

    assertEquals(new IaasUserTenantMembershipId("USER", "TENANT"), membership.getId());
  }

  @Test
  public void testAllArgConstructor() {
    membership = new IaasUserTenantMembership(new IaasUser("USER"), "T1");

    assertEquals(new IaasUser("USER"), membership.getUser());
    assertEquals("T1", membership.getTenantId());
  }

  @Test
  public void testGetSetId() {
    membership.setId(new IaasUserTenantMembershipId("USER", "TENANT"));
    assertEquals(new IaasUserTenantMembershipId("USER", "TENANT"), membership.getId());
  }

  @Test
  public void testGetSetUser() {
    membership.setUser(new IaasUser("USER"));

    assertEquals(new IaasUser("USER"), membership.getUser());
  }

  @Test
  public void testGetSetTenantId() {
    membership.setTenantId("T1");

    assertEquals("T1", membership.getTenantId());
  }
}
