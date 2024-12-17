package io.trishul.iaas.access.aws;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.amazonaws.services.identitymanagement.AmazonIdentityManagement;
import com.amazonaws.services.identitymanagement.model.CreateRoleRequest;
import com.amazonaws.services.identitymanagement.model.CreateRoleResult;
import com.amazonaws.services.identitymanagement.model.DeleteRoleRequest;
import com.amazonaws.services.identitymanagement.model.GetRoleRequest;
import com.amazonaws.services.identitymanagement.model.GetRoleResult;
import com.amazonaws.services.identitymanagement.model.NoSuchEntityException;
import com.amazonaws.services.identitymanagement.model.Policy;
import com.amazonaws.services.identitymanagement.model.Role;
import com.amazonaws.services.identitymanagement.model.UpdateAssumeRolePolicyRequest;
import com.amazonaws.services.identitymanagement.model.UpdateRoleRequest;

public class AwsIamRoleClientTest {
    private AwsIamRoleClient client;

    private AmazonIdentityManagement mAwsIamClient;
    private AwsArnMapper mAwsMapper;
    private IaasEntityMapper<Policy, IaasPolicy> mapper;

    @BeforeEach
    public void init() {
        mAwsIamClient = mock(AmazonIdentityManagement.class);
        mAwsMapper = mock(AwsArnMapper.class);

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
        assertTrue(client.delete("ROLE"));

        verify(mAwsIamClient, times(1)).deleteRole(new DeleteRoleRequest().withRoleName("ROLE"));
    }

    @Test
    public void testDelete_ReturnsFalse_WhenDeleteRequestThrowsNoEntityException() {
        doThrow(NoSuchEntityException.class).when(mAwsIamClient).deleteRole(new DeleteRoleRequest().withRoleName("ROLE"));

        assertFalse(client.delete("ROLE"));
    }

    @Test
    public void testAdd_ReturnsAddedRole() {
        doAnswer(inv -> {
            CreateRoleRequest req = inv.getArgument(0, CreateRoleRequest.class);

            Role role = new Role()
                            .withRoleName(req.getRoleName())
                            .withDescription(req.getDescription())
                            .withAssumeRolePolicyDocument(req.getAssumeRolePolicyDocument())
                            .withRoleId(req.getRoleName() + "_ID")
                            .withArn(req.getRoleName() + "_ARN");

            return new CreateRoleResult().withRole(role);
        }).when(mAwsIamClient).createRole(any());

        IaasRole role = client.add(new IaasRole("ROLE_1", "DESCRIPTION_1", "DOCUMENT_1", null, null, null, null, null));

        IaasRole expected = new IaasRole("ROLE_1", "DESCRIPTION_1", "DOCUMENT_1", "ROLE_1_ARN", "ROLE_1_ID", null, null, null);

        assertEquals(expected, role);
    }

    @Test
    public void testUpdate_ReturnsUpdateRole() {
        doAnswer(inv -> {
            GetRoleRequest req = inv.getArgument(0, GetRoleRequest.class);
            Role role = new Role().withRoleName(req.getRoleName());
            return new GetRoleResult().withRole(role);
        }).when(mAwsIamClient).getRole(any());

        IaasRole role = client.update(new IaasRole("ROLE_1", "DESCRIPTION_1", "DOCUMENT_1", null, null, null, null, null));

        IaasRole expected = new IaasRole("ROLE_1");
        assertEquals(expected, role);

        verify(mAwsIamClient).updateRole(new UpdateRoleRequest().withRoleName("ROLE_1").withDescription("DESCRIPTION_1"));
        verify(mAwsIamClient).updateAssumeRolePolicy(new UpdateAssumeRolePolicyRequest().withRoleName("ROLE_1").withPolicyDocument("DOCUMENT_1"));
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
