package io.trishul.iaas.access.aws;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import com.amazonaws.ResponseMetadata;
import com.amazonaws.services.identitymanagement.AmazonIdentityManagement;
import com.amazonaws.services.identitymanagement.model.CreateRoleRequest;
import com.amazonaws.services.identitymanagement.model.CreateRoleResult;
import com.amazonaws.services.identitymanagement.model.DeleteRoleRequest;
import com.amazonaws.services.identitymanagement.model.DeleteRoleResult;
import com.amazonaws.services.identitymanagement.model.GetRoleRequest;
import com.amazonaws.services.identitymanagement.model.GetRoleResult;
import com.amazonaws.services.identitymanagement.model.NoSuchEntityException;
import com.amazonaws.services.identitymanagement.model.Role;
import com.amazonaws.services.identitymanagement.model.UpdateAssumeRolePolicyRequest;
import com.amazonaws.services.identitymanagement.model.UpdateRoleRequest;
import io.trishul.iaas.access.role.model.IaasRole;

public class AwsIamRoleClientTest {
  private AwsIamRoleClient client;

  private AmazonIdentityManagement mAwsIamClient;

  @BeforeEach
  public void init() {
    mAwsIamClient = mock(AmazonIdentityManagement.class);

    client = new AwsIamRoleClient(mAwsIamClient, AwsIaasRoleMapper.INSTANCE);
  }

  @Test
  public void testGet_ReturnsPolicyFromAwsRequest() {
    doAnswer(inv -> {
      GetRoleRequest req = inv.getArgument(0, GetRoleRequest.class);
      Role role = new Role().withRoleName(req.getRoleName());
      return new GetRoleResult().withRole(role);
    }).when(mAwsIamClient).getRole(any());

    IaasRole role = client.get("ROLE");

    assertEquals(new IaasRole("ROLE"), role);
  }

  @Test
  public void testDelete_ReturnsTrue_WhenDeleteRequestSucceeds() {
    ResponseMetadata mResponseMetadata = mock(ResponseMetadata.class);
    doReturn("REQUEST_ID").when(mResponseMetadata).getRequestId();
    DeleteRoleResult mResult = new DeleteRoleResult();
    mResult.setSdkResponseMetadata(mResponseMetadata);

    DeleteRoleRequest request = new DeleteRoleRequest().withRoleName("ROLE");
    doReturn(mResult).when(mAwsIamClient).deleteRole(request);

    assertTrue(client.delete("ROLE"));

    verify(mAwsIamClient, times(1)).deleteRole(request);
  }

  @Test
  public void testDelete_ReturnsFalse_WhenDeleteRequestThrowsNoEntityException() {
    DeleteRoleRequest request = new DeleteRoleRequest().withRoleName("ROLE");

    doThrow(NoSuchEntityException.class).when(mAwsIamClient).deleteRole(request);

    assertFalse(client.delete("ROLE"));
  }

  @Test
  public void testAdd_ReturnsAddedRole() {
    doAnswer(inv -> {
      CreateRoleRequest req = inv.getArgument(0, CreateRoleRequest.class);

      Role role = new Role().withRoleName(req.getRoleName()).withDescription(req.getDescription())
          .withAssumeRolePolicyDocument(req.getAssumeRolePolicyDocument())
          .withRoleId(req.getRoleName() + "_ID").withArn(req.getRoleName() + "_ARN");

      return new CreateRoleResult().withRole(role);
    }).when(mAwsIamClient).createRole(any());

    IaasRole role = client.add(new IaasRole().setId("ROLE_1").setDescription("DESCRIPTION_1")
        .setAssumePolicyDocument("DOCUMENT_1"));

    IaasRole expected = new IaasRole().setId("ROLE_1").setDescription("DESCRIPTION_1")
        .setAssumePolicyDocument("DOCUMENT_1").setIaasResourceName("ROLE_1_ARN")
        .setIaasId("ROLE_1_ID");

    assertEquals(expected, role);
  }

  @Test
  public void testUpdate_ReturnsUpdateRole() {
    doAnswer(inv -> {
      GetRoleRequest req = inv.getArgument(0, GetRoleRequest.class);
      Role role = new Role().withRoleName(req.getRoleName());
      return new GetRoleResult().withRole(role);
    }).when(mAwsIamClient).getRole(any());

    IaasRole role = client.update(new IaasRole().setId("ROLE_1").setDescription("DESCRIPTION_1")
        .setAssumePolicyDocument("DOCUMENT_1"));

    IaasRole expected = new IaasRole().setId("ROLE_1");

    assertEquals(expected, role);

    verify(mAwsIamClient).updateRole(
        new UpdateRoleRequest().withRoleName("ROLE_1").withDescription("DESCRIPTION_1"));
    verify(mAwsIamClient).updateAssumeRolePolicy(new UpdateAssumeRolePolicyRequest()
        .withRoleName("ROLE_1").withPolicyDocument("DOCUMENT_1"));
  }

  @Test
  public void testExists_ReturnsTrue_WhenGetReturnsObject() {
    doAnswer(inv -> {
      GetRoleRequest req = inv.getArgument(0, GetRoleRequest.class);
      Role role = new Role().withRoleName(req.getRoleName());
      return new GetRoleResult().withRole(role);
    }).when(mAwsIamClient).getRole(any());

    assertTrue(client.exists("ROLE"));
  }

  @Test
  public void testExists_ReturnsFalse_WhenGetReturnsNull() {
    doThrow(NoSuchEntityException.class).when(mAwsIamClient).getRole(any(GetRoleRequest.class));

    assertFalse(client.exists("ROLE"));
  }

  @Test
  public void testPut_CallsAdd_WhenExistIsFalse() {
    client = spy(client);
    doReturn(false).when(client).exists("ROLE_1");

    doAnswer(inv -> inv.getArgument(0, IaasRole.class)).when(client).add(any());

    assertEquals(new IaasRole("ROLE_1"), client.put(new IaasRole("ROLE_1")));
  }

  @Test
  public void testPut_CallsUpdate_WhenExistIsTrue() {
    client = spy(client);
    doReturn(true).when(client).exists("ROLE_1");

    doAnswer(inv -> inv.getArgument(0, IaasRole.class)).when(client).update(any());

    assertEquals(new IaasRole("ROLE_1"), client.put(new IaasRole("ROLE_1")));
  }
}
