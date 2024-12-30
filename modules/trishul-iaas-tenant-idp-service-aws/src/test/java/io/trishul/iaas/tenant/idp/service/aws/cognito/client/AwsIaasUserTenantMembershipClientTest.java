package io.trishul.iaas.tenant.idp.service.aws.cognito.client;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProvider;
import com.amazonaws.services.cognitoidp.model.AdminAddUserToGroupRequest;
import com.amazonaws.services.cognitoidp.model.AdminAddUserToGroupResult;
import com.amazonaws.services.cognitoidp.model.AdminListGroupsForUserRequest;
import com.amazonaws.services.cognitoidp.model.AdminListGroupsForUserResult;
import com.amazonaws.services.cognitoidp.model.AdminRemoveUserFromGroupRequest;
import com.amazonaws.services.cognitoidp.model.GroupType;
import com.amazonaws.services.cognitoidp.model.ResourceNotFoundException;
import io.trishul.iaas.user.model.IaasUser;
import io.trishul.iaas.user.model.IaasUserTenantMembership;
import io.trishul.iaas.user.model.IaasUserTenantMembershipId;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AwsIaasUserTenantMembershipClientTest {
  private AwsIaasUserTenantMembershipClient client;

  private AWSCognitoIdentityProvider mIdp;

  @BeforeEach
  public void init() {
    mIdp = mock(AWSCognitoIdentityProvider.class);

    client = new AwsIaasUserTenantMembershipClient(mIdp, "USER_POOL");
  }

  @Test
  public void testGet_ReturnsMembershipObjectsWithId_WhenExistingGroupIsFound() {
    doAnswer(inv -> {
      AdminListGroupsForUserRequest req = inv.getArgument(0, AdminListGroupsForUserRequest.class);
      assertEquals("USER_POOL", req.getUserPoolId());

      List<GroupType> groups = List.of(new GroupType().withGroupName(
          req.getUsername() + "_T" + StringUtils.defaultIfEmpty(req.getNextToken(), "")));

      return new AdminListGroupsForUserResult().withGroups(groups)
          .withNextToken(nextToken(req.getNextToken()));
    }).when(mIdp).adminListGroupsForUser(any(AdminListGroupsForUserRequest.class));

    IaasUserTenantMembership membership
        = client.get(new IaasUserTenantMembershipId("USER_1", "USER_1_TA"));

    IaasUserTenantMembership expected
        = new IaasUserTenantMembership(new IaasUser("USER_1"), "USER_1_TA");

    assertEquals(expected, membership);
  }

  @Test
  public void testGet_ReturnsNull_WhenNoExistingGroupMatchIsFound() {
    doAnswer(inv -> {
      AdminListGroupsForUserRequest req = inv.getArgument(0, AdminListGroupsForUserRequest.class);
      assertEquals("USER_POOL", req.getUserPoolId());

      List<GroupType> groups = List.of(new GroupType().withGroupName(
          req.getUsername() + "_T" + StringUtils.defaultIfEmpty(req.getNextToken(), "")));

      return new AdminListGroupsForUserResult().withGroups(groups)
          .withNextToken(nextToken(req.getNextToken()));
    }).when(mIdp).adminListGroupsForUser(any(AdminListGroupsForUserRequest.class));

    IaasUserTenantMembership membership
        = client.get(new IaasUserTenantMembershipId("USER_1", "USER_1_NO_TENANT"));

    assertNull(membership);
  }

  @Test
  public void testAdd_AddsUserToGroupAndReturnsMembership() {
    doReturn(new AdminAddUserToGroupResult()).when(mIdp)
        .adminAddUserToGroup(any(AdminAddUserToGroupRequest.class));

    IaasUserTenantMembership membership
        = client.add(new IaasUserTenantMembership(new IaasUser("USER_1"), "T1"));

    IaasUserTenantMembership expected = new IaasUserTenantMembership(new IaasUser("USER_1"), "T1");

    assertEquals(expected, membership);
    verify(mIdp).adminAddUserToGroup(new AdminAddUserToGroupRequest().withGroupName("T1")
        .withUsername("USER_1").withUserPoolId("USER_POOL"));
  }

  @Test
  public void testPut_AddsUserToGroupAndReturnsMembership_WhenExistingIsNull() {
    client = spy(client);
    doReturn(null).when(client).get(new IaasUserTenantMembershipId("USER_1", "USER_1_TA"));
    doAnswer(inv -> inv.getArgument(0, IaasUserTenantMembership.class)).when(client).add(any());

    IaasUserTenantMembership membership
        = client.put(new IaasUserTenantMembership(new IaasUser("USER_1"), "USER_1_TA"));

    IaasUserTenantMembership expected
        = new IaasUserTenantMembership(new IaasUser("USER_1"), "USER_1_TA");

    assertEquals(expected, membership);
    verify(client, times(1)).add(any());
  }

  @Test
  public void testPut_DoesNothingAndReturnsExisting_WhenExistingIsNotNull() {
    client = spy(client);
    doAnswer(
        inv -> new IaasUserTenantMembership((inv.getArgument(0, IaasUserTenantMembershipId.class))))
            .when(client).get(any());

    IaasUserTenantMembership membership
        = client.put(new IaasUserTenantMembership(new IaasUser("USER_1"), "T1"));

    IaasUserTenantMembership expected = new IaasUserTenantMembership(new IaasUser("USER_1"), "T1");

    assertEquals(expected, membership);
    verify(client, times(0)).add(any());
  }

  @Test
  public void testDelete_ReturnsTrue_WhenRemoveUserFromGroupIsCalled() {
    boolean b = client.delete(new IaasUserTenantMembershipId("USER_1", "T1"));

    assertTrue(b);
    verify(mIdp).adminRemoveUserFromGroup(new AdminRemoveUserFromGroupRequest().withGroupName("T1")
        .withUsername("USER_1").withUserPoolId("USER_POOL"));
  }

  @Test
  public void testDelete_ReturnsFalse_WhenRemoveUserFromGroupIsCalled() {
    doThrow(ResourceNotFoundException.class).when(mIdp)
        .adminRemoveUserFromGroup(new AdminRemoveUserFromGroupRequest().withGroupName("T1")
            .withUsername("USER_1").withUserPoolId("USER_POOL"));
    boolean b = client.delete(new IaasUserTenantMembershipId("USER_1", "T1"));

    assertFalse(b);
  }

  @Test
  public void testExists_ReturnsTrue_WhenGetReturnsMembership() {
    doAnswer(inv -> {
      AdminListGroupsForUserRequest req = inv.getArgument(0, AdminListGroupsForUserRequest.class);
      assertEquals("USER_POOL", req.getUserPoolId());

      List<GroupType> groups = List.of(new GroupType().withGroupName(
          req.getUsername() + "_T" + StringUtils.defaultIfEmpty(req.getNextToken(), "")));

      return new AdminListGroupsForUserResult().withGroups(groups)
          .withNextToken(nextToken(req.getNextToken()));
    }).when(mIdp).adminListGroupsForUser(any(AdminListGroupsForUserRequest.class));

    boolean b = client.exists(new IaasUserTenantMembershipId("USER_1", "USER_1_TA"));

    assertTrue(b);
  }

  @Test
  public void testExists_ReturnsFalse_WhenGetReturnsNull() {
    doAnswer(inv -> {
      AdminListGroupsForUserRequest req = inv.getArgument(0, AdminListGroupsForUserRequest.class);
      assertEquals("USER_POOL", req.getUserPoolId());

      return new AdminListGroupsForUserResult().withGroups(new ArrayList<>());
    }).when(mIdp).adminListGroupsForUser(any(AdminListGroupsForUserRequest.class));

    boolean b = client.exists(new IaasUserTenantMembershipId("USER_1", "T1"));

    assertFalse(b);
  }

  private String nextToken(String currentToken) {
    if (currentToken == null) {
      return "A";
    } else if (currentToken == "A") {
      return "B";
    } else if (currentToken == "B") {
      return "C";
    } else {
      return null;
    }
  }
}
