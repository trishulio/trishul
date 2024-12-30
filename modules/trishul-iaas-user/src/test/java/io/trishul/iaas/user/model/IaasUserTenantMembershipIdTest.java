package io.trishul.iaas.user.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class IaasUserTenantMembershipIdTest {
  private IaasUserTenantMembershipId id;

  @BeforeEach
  public void init() {
    id = new IaasUserTenantMembershipId();
  }

  @Test
  public void testNoArgConstructor() {
    assertNull(id.getUserId());
    assertNull(id.getTenantId());
  }

  @Test
  public void testIdArgConstructor() {
    id = new IaasUserTenantMembershipId("USER", "T1");
    assertEquals("USER", id.getUserId());
    assertEquals("T1", id.getTenantId());
  }

  @Test
  public void testBuild_ReturnsNull_WhenBothUserAndTenantIdAreNull() {
    assertNull(IaasUserTenantMembershipId.build(null, null));
  }

  @Test
  public void testBuild_ReturnsId_WhenArgIsNotNull() {
    assertEquals(new IaasUserTenantMembershipId().setUserId("USER").setTenantId("T1"),
        IaasUserTenantMembershipId.build(new IaasUser().setId("USER"), "T1"));
    assertEquals(new IaasUserTenantMembershipId().setUserId("USER"),
        IaasUserTenantMembershipId.build(new IaasUser().setId("USER"), null));
    assertEquals(new IaasUserTenantMembershipId().setTenantId("T1"),
        IaasUserTenantMembershipId.build(new IaasUser(), "T1"));
    assertEquals(new IaasUserTenantMembershipId().setTenantId("T1"),
        IaasUserTenantMembershipId.build(null, "T1"));
  }
}
