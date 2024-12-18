package io.trishul.iaas.access.aws;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import org.mockito.InOrder;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.amazonaws.services.identitymanagement.AmazonIdentityManagement;
import com.amazonaws.services.identitymanagement.model.CreatePolicyRequest;
import com.amazonaws.services.identitymanagement.model.CreatePolicyResult;
import com.amazonaws.services.identitymanagement.model.CreatePolicyVersionRequest;
import com.amazonaws.services.identitymanagement.model.DeletePolicyRequest;
import com.amazonaws.services.identitymanagement.model.DeletePolicyVersionRequest;
import com.amazonaws.services.identitymanagement.model.GetPolicyRequest;
import com.amazonaws.services.identitymanagement.model.GetPolicyResult;
import com.amazonaws.services.identitymanagement.model.ListPolicyVersionsRequest;
import com.amazonaws.services.identitymanagement.model.ListPolicyVersionsResult;
import com.amazonaws.services.identitymanagement.model.NoSuchEntityException;
import com.amazonaws.services.identitymanagement.model.Policy;
import com.amazonaws.services.identitymanagement.model.PolicyVersion;

import io.trishul.iaas.access.policy.model.IaasPolicy;
import io.trishul.iaas.mapper.IaasEntityMapper;

public class AwsIamPolicyClientTest {
    private AwsIamPolicyClient client;

    private AmazonIdentityManagement mAwsIamClient;
    private AwsArnMapper mAwsMapper;
    private IaasEntityMapper<Policy, IaasPolicy> mapper;

    @BeforeEach
    public void init() {
        mAwsIamClient = mock(AmazonIdentityManagement.class);
        mAwsMapper = mock(AwsArnMapper.class);
        doAnswer(inv -> inv.getArgument(0, String.class) + "_ARN").when(mAwsMapper).getPolicyArn(anyString());
        doAnswer(inv -> inv.getArgument(0, String.class).replace("_ARN", "")).when(mAwsMapper).getName(anyString());

        client = new AwsIamPolicyClient(mAwsIamClient, mAwsMapper, AwsIaasPolicyMapper.INSTANCE);
    }

    @Test
    public void testGet_ReturnsPolicyAndDefaultDocumentFromAwsRequest() {
        doAnswer(inv -> {
            GetPolicyRequest req = inv.getArgument(0, GetPolicyRequest.class);
            Policy policy = new Policy().withPolicyName(mAwsMapper.getName(req.getPolicyArn()));
            return new GetPolicyResult().withPolicy(policy);
        }).when(mAwsIamClient).getPolicy(any());

        doAnswer(inv -> {
            ListPolicyVersionsRequest req = inv.getArgument(0, ListPolicyVersionsRequest.class);

            List<PolicyVersion> versions = List.of(
                new PolicyVersion().withDocument("DEFAULT_DOCUMENT_" + req.getPolicyArn()).withIsDefaultVersion(true),
                new PolicyVersion().withDocument("DOCUMENT_" + req.getPolicyArn()).withIsDefaultVersion(false)
            );

            return new ListPolicyVersionsResult().withVersions(versions);
        }).when(mAwsIamClient).listPolicyVersions(any());

        IaasPolicy policy = client.get("POLICY");

        IaasPolicy expected = new IaasPolicy("POLICY");
        expected.setDocument("DEFAULT_DOCUMENT_POLICY_ARN");
        assertEquals(expected, policy);

        verify(mAwsIamClient, times(1)).getPolicy(any());
        verify(mAwsIamClient, times(1)).listPolicyVersions(any());
    }

    @Test
    public void testDelete_ReturnsTrue_WhenDeleteRequestSucceeds() {
        assertTrue(client.delete("POLICY"));

        verify(mAwsIamClient, times(1)).deletePolicy(new DeletePolicyRequest().withPolicyArn("POLICY_ARN"));
    }

    @Test
    public void testDelete_ReturnsFalse_WhenDeleteRequestThrowsNoEntityException() {
        doThrow(NoSuchEntityException.class).when(mAwsIamClient).deletePolicy(new DeletePolicyRequest().withPolicyArn("POLICY_ARN"));

        assertFalse(client.delete("POLICY"));
    }

    @Test
    public void testAdd_ReturnsAddedPolicy() {
        doAnswer(inv -> {
            CreatePolicyRequest req = inv.getArgument(0, CreatePolicyRequest.class);

            Policy policy = new Policy()
                            .withPolicyName(req.getPolicyName())
                            .withDescription(req.getDescription())
                            .withPolicyId(req.getPolicyName() + "_ID")
                            .withArn(req.getPolicyName() + "_ARN");

            return new CreatePolicyResult().withPolicy(policy);
        }).when(mAwsIamClient).createPolicy(any());

        doAnswer(inv -> {
            ListPolicyVersionsRequest req = inv.getArgument(0, ListPolicyVersionsRequest.class);

            List<PolicyVersion> versions = List.of(
                new PolicyVersion().withDocument("DEFAULT_DOCUMENT_" + req.getPolicyArn()).withIsDefaultVersion(true),
                new PolicyVersion().withDocument("DOCUMENT_" + req.getPolicyArn()).withIsDefaultVersion(false)
            );

            return new ListPolicyVersionsResult().withVersions(versions);
        }).when(mAwsIamClient).listPolicyVersions(any());

        IaasPolicy policy = client.add(new IaasPolicy("POLICY_1", "DOCUMENT", "DESCRIPTION_1", "POLICY_1_ARN", "POLICY_1_ID", null, null));

        IaasPolicy expected = new IaasPolicy("POLICY_1", "DEFAULT_DOCUMENT_POLICY_1_ARN", "DESCRIPTION_1", "POLICY_1_ARN", "POLICY_1_ID", null, null);

        assertEquals(expected, policy);
        verify(mAwsIamClient, times(1)).createPolicy(any());
    }

    @Test
    public void testUpdate_ReturnsUpdatedPolicy_AfterAddingNewDefaultVersionAndDeletingAllOtherVersions() {
        doAnswer(inv -> {
            GetPolicyRequest req = inv.getArgument(0, GetPolicyRequest.class);
            Policy policy = new Policy().withPolicyName(mAwsMapper.getName(req.getPolicyArn()));
            return new GetPolicyResult().withPolicy(policy);
        }).when(mAwsIamClient).getPolicy(any());

        doAnswer(inv -> {
            ListPolicyVersionsRequest req = inv.getArgument(0, ListPolicyVersionsRequest.class);

            List<PolicyVersion> versions = List.of(
                new PolicyVersion().withDocument("DEFAULT_DOCUMENT_" + req.getPolicyArn()).withIsDefaultVersion(true).withVersionId("DEFAULT_V1"),
                new PolicyVersion().withDocument("DOCUMENT_" + req.getPolicyArn()).withIsDefaultVersion(false).withVersionId("NON_DEFAULT_V1")
            );

            return new ListPolicyVersionsResult().withVersions(versions);
        }).when(mAwsIamClient).listPolicyVersions(any());

        IaasPolicy policy = client.update(new IaasPolicy("POLICY_1", "DOCUMENT_1", "DESCRIPTION_1", "IAAS_RES_1", "IAAS_ID_1", LocalDateTime.of(2000, 1, 1, 0, 0), LocalDateTime.of(2001, 1, 1, 0, 0)));

        IaasPolicy expected = new IaasPolicy("POLICY_1");
        expected.setDocument("DEFAULT_DOCUMENT_POLICY_1_ARN");
        assertEquals(expected, policy);

        InOrder order = inOrder(mAwsIamClient);
        order.verify(mAwsIamClient).createPolicyVersion(new CreatePolicyVersionRequest().withPolicyArn("POLICY_1_ARN").withPolicyDocument("DOCUMENT_1").withSetAsDefault(true));
        order.verify(mAwsIamClient).deletePolicyVersion(new DeletePolicyVersionRequest().withPolicyArn("POLICY_1_ARN").withVersionId("NON_DEFAULT_V1"));
    }

    @Test
    public void testExists_ReturnsTrue_WhenGetReturnsObject() {
        doAnswer(inv -> {
            GetPolicyRequest req = inv.getArgument(0, GetPolicyRequest.class);
            Policy policy = new Policy().withPolicyName(mAwsMapper.getName(req.getPolicyArn()));
            return new GetPolicyResult().withPolicy(policy);
        }).when(mAwsIamClient).getPolicy(any());

        assertTrue(client.exists("POLICY_NAME"));
    }

    @Test
    public void testExists_ReturnsFalse_WhenGetReturnsNull() {
        doThrow(NoSuchEntityException.class).when(mAwsIamClient).getPolicy(any(GetPolicyRequest.class));

        assertFalse(client.exists("POLICY"));
    }

    @Test
    public void testPut_CallsAdd_WhenExistIsFalse() {
        client = spy(client);
        doReturn(false).when(client).exists("POLICY_1");

        doAnswer(inv -> inv.getArgument(0, IaasPolicy.class)).when(client).add(any());

        assertEquals(new IaasPolicy("POLICY_1"), client.put(new IaasPolicy("POLICY_1")));
    }

    @Test
    public void testPut_CallsUpdate_WhenExistIsTrue() {
        client = spy(client);
        doReturn(true).when(client).exists("POLICY_1");

        doAnswer(inv -> inv.getArgument(0, IaasPolicy.class)).when(client).update(any());

        assertEquals(new IaasPolicy("POLICY_1"), client.put(new IaasPolicy("POLICY_1")));
    }

    @Test
    public void testGetPolicyVersions_ReturnsAllVersions() {
        List<PolicyVersion> pageA = List.of(new PolicyVersion().withVersionId("A1"), new PolicyVersion().withVersionId("A2"));
        List<PolicyVersion> pageB = List.of(new PolicyVersion().withVersionId("B1"), new PolicyVersion().withVersionId("B2"));
        List<PolicyVersion> pageC = List.of(new PolicyVersion().withVersionId("C1"), new PolicyVersion().withVersionId("C2"));

        doReturn(new ListPolicyVersionsResult().withVersions(pageA).withMarker("next_1").withIsTruncated(true)).when(mAwsIamClient).listPolicyVersions(new ListPolicyVersionsRequest().withPolicyArn("POLICY_1_ARN"));
        doReturn(new ListPolicyVersionsResult().withVersions(pageB).withMarker("next_2").withIsTruncated(true)).when(mAwsIamClient).listPolicyVersions(new ListPolicyVersionsRequest().withPolicyArn("POLICY_1_ARN").withMarker("next_1"));
        doReturn(new ListPolicyVersionsResult().withVersions(pageC).withIsTruncated(false)).when(mAwsIamClient).listPolicyVersions(new ListPolicyVersionsRequest().withPolicyArn("POLICY_1_ARN").withMarker("next_2"));

        List<PolicyVersion> versions = client.getPolicyVersions("POLICY_1");

        List<PolicyVersion> expected = List.of(
            new PolicyVersion().withVersionId("A1"),
            new PolicyVersion().withVersionId("A2"),
            new PolicyVersion().withVersionId("B1"),
            new PolicyVersion().withVersionId("B2"),
            new PolicyVersion().withVersionId("C1"),
            new PolicyVersion().withVersionId("C2")
        );
        assertEquals(expected, versions);
    }
}
