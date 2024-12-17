package io.trishul.iaas.access.aws;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.amazonaws.services.identitymanagement.AmazonIdentityManagement;
import com.amazonaws.services.identitymanagement.model.AttachRolePolicyRequest;
import com.amazonaws.services.identitymanagement.model.AttachedPolicy;
import com.amazonaws.services.identitymanagement.model.DetachRolePolicyRequest;
import com.amazonaws.services.identitymanagement.model.ListAttachedRolePoliciesRequest;
import com.amazonaws.services.identitymanagement.model.ListAttachedRolePoliciesResult;
import com.amazonaws.services.identitymanagement.model.NoSuchEntityException;

public class AwsIamRolePolicyAttachmentClientTest {
    private AwsIamRolePolicyAttachmentClient client;

    private AmazonIdentityManagement mAwsClient;
    private AwsArnMapper mArnMapper;

    @BeforeEach
    public void init() {
        mAwsClient = mock(AmazonIdentityManagement.class);
        mArnMapper = mock(AwsArnMapper.class);
        doAnswer(inv -> inv.getArgument(0, String.class) + "_ARN").when(mArnMapper).getPolicyArn(anyString());

        client = new AwsIamRolePolicyAttachmentClient(mAwsClient, mArnMapper);
    }

    @Test
    public void testGet_ReturnsPoliciesGeneratedFromFetchedRoleAndPolicies() {
        List<AttachedPolicy> attachedPolicies = List.of(new AttachedPolicy().withPolicyName("POLICY_1"), new AttachedPolicy().withPolicyName("POLICY_2"));
        doReturn(new ListAttachedRolePoliciesResult().withAttachedPolicies(attachedPolicies).withIsTruncated(true).withMarker("MARKER")).when(mAwsClient).listAttachedRolePolicies(new ListAttachedRolePoliciesRequest().withRoleName("ROLE_1"));
        doThrow(NoSuchEntityException.class).when(mAwsClient).listAttachedRolePolicies(new ListAttachedRolePoliciesRequest().withRoleName("ROLE_1").withMarker("MARKER"));

        IaasRolePolicyAttachment attachment = client.get(new IaasRolePolicyAttachmentId("ROLE_1", "POLICY_1"));

        assertEquals(new IaasRolePolicyAttachment(new IaasRole("ROLE_1"), new IaasPolicy("POLICY_1")), attachment);
    }

    @Test
    public void testGet_ThrowsException_WhenCacheThrowsException() {
        doThrow(RuntimeException.class).when(mAwsClient).listAttachedRolePolicies(new ListAttachedRolePoliciesRequest().withRoleName("ROLE_1"));

        assertThrows(RuntimeException.class, () -> client.get(new IaasRolePolicyAttachmentId("ROLE_1", "POLICY_1")));
    }

    @Test
    public void testAdd_ReturnsAddedAttachement() {
        doAnswer(inv -> inv.getArgument(0, String.class) + "_ARN").when(mArnMapper).getPolicyArn(anyString());

        IaasRolePolicyAttachment addition = new IaasRolePolicyAttachment(new IaasRole("ROLE_1"), new IaasPolicy("POLICY_1"));
        IaasRolePolicyAttachment attachment = client.add(addition);

        IaasRolePolicyAttachment expected = new IaasRolePolicyAttachment(new IaasRole("ROLE_1"), new IaasPolicy("POLICY_1"));
        assertEquals(expected, attachment);
        verify(mAwsClient).attachRolePolicy(new AttachRolePolicyRequest().withPolicyArn("POLICY_1_ARN").withRoleName("ROLE_1"));
    }

    @Test
    public void testPut_ReturnsEntity_WhenGetReturnsEntity() {
        client = spy(client);
        doReturn(new IaasRolePolicyAttachment(new IaasRole("ROLE_1"), new IaasPolicy("POLICY_1"))).when(client).get(new IaasRolePolicyAttachmentId("ROLE_1", "POLICY_1"));

        IaasRolePolicyAttachment attachment = client.put(new IaasRolePolicyAttachment(new IaasRole("ROLE_1"), new IaasPolicy("POLICY_1")));

        assertEquals(new IaasRolePolicyAttachment(new IaasRole("ROLE_1"), new IaasPolicy("POLICY_1")), attachment);
    }

    @Test
    public void testPut_ReturnsAddedEntity_WhenGetReturnNull() {
        client = spy(client);
        doReturn(null).when(client).get(new IaasRolePolicyAttachmentId("ROLE_1", "POLICY_1"));

        doAnswer(inv -> inv.getArgument(0, String.class) + "_ARN").when(mArnMapper).getPolicyArn(anyString());

        IaasRolePolicyAttachment addition = new IaasRolePolicyAttachment(new IaasRole("ROLE_1"), new IaasPolicy("POLICY_1"));
        IaasRolePolicyAttachment attachment = client.put(new IaasRolePolicyAttachment(new IaasRole("ROLE_1"), new IaasPolicy("POLICY_1")));

        IaasRolePolicyAttachment expected = new IaasRolePolicyAttachment(new IaasRole("ROLE_1"), new IaasPolicy("POLICY_1"));
        assertEquals(expected, attachment);
        verify(mAwsClient).attachRolePolicy(new AttachRolePolicyRequest().withPolicyArn("POLICY_1_ARN").withRoleName("ROLE_1"));
    }

    @Test
    public void testExists_ReturnsFalse_WhenGetReturnsNull() {
        client = spy(client);
        doReturn(null).when(client).get(new IaasRolePolicyAttachmentId("ROLE_1", "POLICY_1"));

        assertFalse(client.exists(new IaasRolePolicyAttachmentId("ROLE_1", "POLICY_1")));
    }

    @Test
    public void testExists_ReturnsTrue_WhenGetReturnsEntity() {
        client = spy(client);
        doReturn(new IaasRolePolicyAttachment()).when(client).get(new IaasRolePolicyAttachmentId("ROLE_1", "POLICY_1"));

        assertTrue(client.exists(new IaasRolePolicyAttachmentId("ROLE_1", "POLICY_1")));
    }

    @Test
    public void testDelete_ReturnsTrue_WhenDeleteIsSuccessful() {
        assertTrue(client.delete(new IaasRolePolicyAttachmentId("ROLE_1", "POLICY_1")));

        verify(mAwsClient, times(1)).detachRolePolicy(new DetachRolePolicyRequest().withPolicyArn("POLICY_1_ARN").withRoleName("ROLE_1"));
    }

    @Test
    public void testDelete_ReturnsFalse_WhenEntityDoesNotExist() {
        doThrow(NoSuchEntityException.class).when(mAwsClient).detachRolePolicy(new DetachRolePolicyRequest().withPolicyArn("POLICY_1_ARN").withRoleName("ROLE_1"));
        assertFalse(client.delete(new IaasRolePolicyAttachmentId("ROLE_1", "POLICY_1")));

        verify(mAwsClient, times(1)).detachRolePolicy(new DetachRolePolicyRequest().withPolicyArn("POLICY_1_ARN").withRoleName("ROLE_1"));
    }

    @Test
    public void testCaching_SingleThreaded_CacheCleansUpAfterMutations() {
        // Testing with large data sets to make sure mapping logic is not broken
        List<AttachedPolicy> roleAPolicies = List.of(new AttachedPolicy().withPolicyName("POLICY_1A"), new AttachedPolicy().withPolicyName("POLICY_2A"));
        List<AttachedPolicy> roleBPolicies = List.of(new AttachedPolicy().withPolicyName("POLICY_1B"), new AttachedPolicy().withPolicyName("POLICY_2B"));
        List<AttachedPolicy> roleCPoliciesPartion1 = List.of(new AttachedPolicy().withPolicyName("POLICY_1C"));
        List<AttachedPolicy> roleCPoliciesPartion2 = List.of(new AttachedPolicy().withPolicyName("POLICY_2C"));

        doReturn(new ListAttachedRolePoliciesResult().withAttachedPolicies(roleAPolicies)).when(mAwsClient).listAttachedRolePolicies(new ListAttachedRolePoliciesRequest().withRoleName("ROLE_A"));
        doReturn(new ListAttachedRolePoliciesResult().withAttachedPolicies(roleBPolicies)).when(mAwsClient).listAttachedRolePolicies(new ListAttachedRolePoliciesRequest().withRoleName("ROLE_B"));
        doReturn(new ListAttachedRolePoliciesResult().withAttachedPolicies(roleCPoliciesPartion1).withIsTruncated(true).withMarker("MARKER")).when(mAwsClient).listAttachedRolePolicies(new ListAttachedRolePoliciesRequest().withRoleName("ROLE_C"));
        doReturn(new ListAttachedRolePoliciesResult().withAttachedPolicies(roleCPoliciesPartion2)).when(mAwsClient).listAttachedRolePolicies(new ListAttachedRolePoliciesRequest().withRoleName("ROLE_C").withMarker("MARKER"));

        IaasRolePolicyAttachment roleAPolicy1 = client.get(new IaasRolePolicyAttachmentId("ROLE_A", "POLICY_1A"));
        IaasRolePolicyAttachment roleAPolicy2 = client.get(new IaasRolePolicyAttachmentId("ROLE_A", "POLICY_2A"));
        IaasRolePolicyAttachment roleBPolicy1 = client.get(new IaasRolePolicyAttachmentId("ROLE_B", "POLICY_1B"));
        IaasRolePolicyAttachment roleBPolicy2 = client.get(new IaasRolePolicyAttachmentId("ROLE_B", "POLICY_2B"));
        IaasRolePolicyAttachment roleCPolicy1 = client.get(new IaasRolePolicyAttachmentId("ROLE_C", "POLICY_1C"));
        IaasRolePolicyAttachment roleCPolicy2 = client.get(new IaasRolePolicyAttachmentId("ROLE_C", "POLICY_2C"));
        assertEquals(new IaasRolePolicyAttachment(new IaasRole("ROLE_A"), new IaasPolicy("POLICY_1A")), roleAPolicy1);
        assertEquals(new IaasRolePolicyAttachment(new IaasRole("ROLE_A"), new IaasPolicy("POLICY_2A")), roleAPolicy2);
        assertEquals(new IaasRolePolicyAttachment(new IaasRole("ROLE_B"), new IaasPolicy("POLICY_1B")), roleBPolicy1);
        assertEquals(new IaasRolePolicyAttachment(new IaasRole("ROLE_B"), new IaasPolicy("POLICY_2B")), roleBPolicy2);
        assertEquals(new IaasRolePolicyAttachment(new IaasRole("ROLE_C"), new IaasPolicy("POLICY_1C")), roleCPolicy1);
        assertEquals(new IaasRolePolicyAttachment(new IaasRole("ROLE_C"), new IaasPolicy("POLICY_2C")), roleCPolicy2);

        verify(mAwsClient, times(4)).listAttachedRolePolicies(any());

        assertNull(client.get(new IaasRolePolicyAttachmentId("ROLE_B", "POLICY_1A")));
        assertNull(client.get(new IaasRolePolicyAttachmentId("ROLE_C", "POLICY_2A")));
        assertNull(client.get(new IaasRolePolicyAttachmentId("ROLE_A", "POLICY_1B")));
        assertNull(client.get(new IaasRolePolicyAttachmentId("ROLE_C", "POLICY_1B")));
        assertNull(client.get(new IaasRolePolicyAttachmentId("ROLE_A", "POLICY_1C")));
        assertNull(client.get(new IaasRolePolicyAttachmentId("ROLE_B", "POLICY_2C")));

        verify(mAwsClient, times(4)).listAttachedRolePolicies(any());

        // Testing that add cleans up the cache.
        client.add(new IaasRolePolicyAttachment(new IaasRole("ROLE_A"), new IaasPolicy("POLICY_3A")));
        roleAPolicy1 = client.get(new IaasRolePolicyAttachmentId("ROLE_A", "POLICY_1A"));
        assertEquals(new IaasRolePolicyAttachment(new IaasRole("ROLE_A"), new IaasPolicy("POLICY_1A")), roleAPolicy1);
        verify(mAwsClient, times(5)).listAttachedRolePolicies(any());

        // Testing that put cleans up the cache.
        client.put(new IaasRolePolicyAttachment(new IaasRole("ROLE_A"), new IaasPolicy("POLICY_3A")));
        roleAPolicy1 = client.get(new IaasRolePolicyAttachmentId("ROLE_A", "POLICY_1A"));
        assertEquals(new IaasRolePolicyAttachment(new IaasRole("ROLE_A"), new IaasPolicy("POLICY_1A")), roleAPolicy1);
        verify(mAwsClient, times(6)).listAttachedRolePolicies(any());

        // Testing that delete cleans up the cache.
        client.delete(new IaasRolePolicyAttachmentId("ROLE_A", "POLICY_1A"));
        roleAPolicy1 = client.get(new IaasRolePolicyAttachmentId("ROLE_A", "POLICY_1A"));
        assertEquals(new IaasRolePolicyAttachment(new IaasRole("ROLE_A"), new IaasPolicy("POLICY_1A")), roleAPolicy1);
        verify(mAwsClient, times(7)).listAttachedRolePolicies(any());
   }
}
