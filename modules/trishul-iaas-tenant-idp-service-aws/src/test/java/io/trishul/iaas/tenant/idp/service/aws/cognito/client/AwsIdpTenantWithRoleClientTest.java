package io.trishul.iaas.tenant.idp.service.aws.cognito.client;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProvider;
import com.amazonaws.services.cognitoidp.model.CreateGroupRequest;
import com.amazonaws.services.cognitoidp.model.CreateGroupResult;
import com.amazonaws.services.cognitoidp.model.GetGroupRequest;
import com.amazonaws.services.cognitoidp.model.GetGroupResult;
import com.amazonaws.services.cognitoidp.model.GroupType;
import com.amazonaws.services.cognitoidp.model.UpdateGroupRequest;
import com.amazonaws.services.cognitoidp.model.UpdateGroupResult;
import io.trishul.iaas.access.aws.AwsArnMapper;
import io.trishul.iaas.access.service.role.service.IaasRoleService;
import io.trishul.iaas.access.role.model.IaasRole;
import io.trishul.iaas.idp.tenant.model.IaasIdpTenant;
import io.trishul.iaas.idp.tenant.model.UpdateIaasIdpTenant;
import io.trishul.iaas.idp.tenant.model.BaseIaasIdpTenant;

public class AwsIdpTenantWithRoleClientTest {
    private AwsIdpTenantWithRoleClient client;

    private AWSCognitoIdentityProvider mIdp;
    private AwsArnMapper mArnMapper;
    private IaasRoleService mRoleService;

    @BeforeEach
    public void init() {
        mIdp = mock(AWSCognitoIdentityProvider.class);
        mRoleService = mock(IaasRoleService.class);
        mArnMapper = mock(AwsArnMapper.class);
        doAnswer(inv -> "ARN_" + inv.getArgument(0, String.class)).when(mArnMapper).getName(anyString());

        client = new AwsIdpTenantWithRoleClient(mIdp, "USER_POOL", AwsGroupTypeMapper.INSTANCE, mArnMapper, mRoleService);
    }

    @Test
    public void testGet_ReturnsTenantWithRole() {
        doAnswer(inv -> {
            GetGroupRequest req = inv.getArgument(0, GetGroupRequest.class);
            assertEquals("USER_POOL", req.getUserPoolId());
            return new GetGroupResult().withGroup(
                new GroupType()
                .withGroupName(req.getGroupName())
                .withDescription(req.getGroupName() + "_DESCRIPTION")
                .withRoleArn(req.getGroupName() + "_ROLE")
                .withUserPoolId(req.getUserPoolId())
                .withCreationDate(new Date(100, 0, 1))
                .withLastModifiedDate(new Date(100, 1, 2))
          );
        }).when(mIdp).getGroup(any(GetGroupRequest.class));

        doAnswer(inv -> new IaasRole(inv.getArgument(0, String.class))).when(mRoleService).get(anyString());

        IaasIdpTenant tenant = client.get("T1");

        IaasIdpTenant expected = new IaasIdpTenant(
            "T1",
            new IaasRole("ARN_T1_ROLE"),
            "T1_DESCRIPTION",
            LocalDateTime.of(2000, 1, 1, 0, 0),
            LocalDateTime.of(2000, 2, 2, 0, 0)
        );
        assertEquals(expected, tenant);
    }

    @Test
    public void testAdd_AddsAndReturnsEntity() {
        doAnswer(inv -> {
            CreateGroupRequest req = inv.getArgument(0, CreateGroupRequest.class);
            assertEquals("USER_POOL", req.getUserPoolId());

            return new CreateGroupResult().withGroup(
                new GroupType()
                .withGroupName(req.getGroupName())
                .withDescription(req.getGroupName() + "_DESCRIPTION")
                .withRoleArn(req.getGroupName() + "_ROLE")
                .withUserPoolId(req.getUserPoolId())
                .withCreationDate(new Date(100, 0, 1))
                .withLastModifiedDate(new Date(100, 1, 2))
            );
        }).when(mIdp).createGroup(any(CreateGroupRequest.class));

        doAnswer(inv -> new IaasRole(inv.getArgument(0, String.class))).when(mRoleService).get(anyString());

        IaasIdpTenant tenant = client.add(new IaasIdpTenant("T1", new IaasRole("ARN_T1_ROLE"), "T1_DESCRIPTION", LocalDateTime.of(2000, 1, 1, 0, 0), LocalDateTime.of(2000, 2, 2, 0, 0)));

        IaasIdpTenant expected = new IaasIdpTenant(
            "T1",
            new IaasRole("ARN_T1_ROLE"),
            "T1_DESCRIPTION",
            LocalDateTime.of(2000, 1, 1, 0, 0),
            LocalDateTime.of(2000, 2, 2, 0, 0)
        );
        assertEquals(expected, tenant);
    }

    @Test
    public void testUpdate_UpdatesAndReturnsEntity() {
        doAnswer(inv -> {
            UpdateGroupRequest req = inv.getArgument(0, UpdateGroupRequest.class);
            assertEquals("USER_POOL", req.getUserPoolId());

            return new UpdateGroupResult().withGroup(
                new GroupType()
                .withGroupName(req.getGroupName())
                .withDescription(req.getGroupName() + "_DESCRIPTION")
                .withRoleArn(req.getGroupName() + "_ROLE")
                .withUserPoolId(req.getUserPoolId())
                .withCreationDate(new Date(100, 0, 1))
                .withLastModifiedDate(new Date(100, 1, 2))
            );
        }).when(mIdp).updateGroup(any(UpdateGroupRequest.class));

        doAnswer(inv -> new IaasRole(inv.getArgument(0, String.class))).when(mRoleService).get(anyString());

        IaasIdpTenant tenant = client.update(new IaasIdpTenant("T1", new IaasRole("ARN_T1_ROLE"), "T1_DESCRIPTION", LocalDateTime.of(2000, 1, 1, 0, 0), LocalDateTime.of(2000, 2, 2, 0, 0)));

        IaasIdpTenant expected = new IaasIdpTenant(
            "T1",
            new IaasRole("ARN_T1_ROLE"),
            "T1_DESCRIPTION",
            LocalDateTime.of(2000, 1, 1, 0, 0),
            LocalDateTime.of(2000, 2, 2, 0, 0)
        );
        assertEquals(expected, tenant);
    }

    @Test
    public void testPut_CallsUpdate_WhenExistsReturnsTrue() {
        client = spy(client);
        doReturn(true).when(client).exists("T1");
        doAnswer(inv -> inv.getArgument(0, IaasIdpTenant.class)).when(client).update(any(UpdateIaasIdpTenant.class));

        IaasIdpTenant tenant = client.put(new IaasIdpTenant("T1", new IaasRole("ARN_T1_ROLE"), "T1_DESCRIPTION", LocalDateTime.of(2000, 1, 1, 0, 0), LocalDateTime.of(2000, 2, 2, 0, 0)));

        IaasIdpTenant expected = new IaasIdpTenant(
            "T1",
            new IaasRole("ARN_T1_ROLE"),
            "T1_DESCRIPTION",
            LocalDateTime.of(2000, 1, 1, 0, 0),
            LocalDateTime.of(2000, 2, 2, 0, 0)
        );
        assertEquals(expected, tenant);
    }

    @Test
    public void testPut_CallsAdd_WhenExistsReturnsFalse() {
        client = spy(client);
        doReturn(false).when(client).exists("T1");
        doAnswer(inv -> inv.getArgument(0, IaasIdpTenant.class)).when(client).add(any(BaseIaasIdpTenant.class));

        IaasIdpTenant tenant = client.put(new IaasIdpTenant("T1", new IaasRole("ARN_T1_ROLE"), "T1_DESCRIPTION", LocalDateTime.of(2000, 1, 1, 0, 0), LocalDateTime.of(2000, 2, 2, 0, 0)));

        IaasIdpTenant expected = new IaasIdpTenant(
            "T1",
            new IaasRole("ARN_T1_ROLE"),
            "T1_DESCRIPTION",
            LocalDateTime.of(2000, 1, 1, 0, 0),
            LocalDateTime.of(2000, 2, 2, 0, 0)
        );
        assertEquals(expected, tenant);
    }

    @Test
    public void testExists_ReturnsFalse_WhenGetIsNull() {
        client = spy(client);
        doReturn(null).when(client).get("T1");

        assertFalse(client.exists("T1"));
    }

    @Test
    public void testExists_ReturnsTrue_WhenGetIsNotNull() {
        client = spy(client);
        doReturn(new IaasIdpTenant()).when(client).get("T1");

        assertTrue(client.exists("T1"));
    }
}
