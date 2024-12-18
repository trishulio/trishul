package io.trishul.iaas.access.aws;

import com.amazonaws.services.identitymanagement.AmazonIdentityManagement;
import com.amazonaws.services.identitymanagement.model.CreatePolicyRequest;
import com.amazonaws.services.identitymanagement.model.CreatePolicyResult;
import com.amazonaws.services.identitymanagement.model.CreatePolicyVersionRequest;
import com.amazonaws.services.identitymanagement.model.CreatePolicyVersionResult;
import com.amazonaws.services.identitymanagement.model.DeletePolicyRequest;
import com.amazonaws.services.identitymanagement.model.DeletePolicyResult;
import com.amazonaws.services.identitymanagement.model.DeletePolicyVersionRequest;
import com.amazonaws.services.identitymanagement.model.DeletePolicyVersionResult;
import com.amazonaws.services.identitymanagement.model.GetPolicyRequest;
import com.amazonaws.services.identitymanagement.model.GetPolicyResult;
import com.amazonaws.services.identitymanagement.model.ListPolicyVersionsRequest;
import com.amazonaws.services.identitymanagement.model.ListPolicyVersionsResult;
import com.amazonaws.services.identitymanagement.model.NoSuchEntityException;
import com.amazonaws.services.identitymanagement.model.Policy;
import com.amazonaws.services.identitymanagement.model.PolicyVersion;
import io.trishul.iaas.access.policy.model.BaseIaasPolicy;
import io.trishul.iaas.access.policy.model.IaasPolicy;
import io.trishul.iaas.access.policy.model.UpdateIaasPolicy;
import io.trishul.iaas.client.IaasClient;
import io.trishul.iaas.mapper.IaasEntityMapper;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.BooleanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AwsIamPolicyClient
        implements IaasClient<String, IaasPolicy, BaseIaasPolicy, UpdateIaasPolicy> {
    private static final Logger log = LoggerFactory.getLogger(AwsIamPolicyClient.class);

    private final AmazonIdentityManagement awsIamClient;
    private final AwsArnMapper awsMapper;
    private final IaasEntityMapper<Policy, IaasPolicy> mapper;

    public AwsIamPolicyClient(
            AmazonIdentityManagement awsIamClient,
            AwsArnMapper awsMapper,
            IaasEntityMapper<Policy, IaasPolicy> mapper) {
        this.awsIamClient = awsIamClient;
        this.awsMapper = awsMapper;
        this.mapper = mapper;
    }

    @Override
    public IaasPolicy get(String policyName) {
        IaasPolicy policy = null;

        String policyArn = awsMapper.getPolicyArn(policyName);
        GetPolicyRequest request = new GetPolicyRequest().withPolicyArn(policyArn);
        try {
            GetPolicyResult result = this.awsIamClient.getPolicy(request);
            policy = mapper.fromIaasEntity(result.getPolicy());
            policy.setDocument(getDefaultDocument(policyName));

        } catch (NoSuchEntityException e) {
            log.error("No policy found with arn: {}", policyArn);
        }

        return policy;
    }

    @Override
    public boolean delete(String policyName) {
        boolean success = false;

        String policyArn = awsMapper.getPolicyArn(policyName);
        DeletePolicyRequest request = new DeletePolicyRequest().withPolicyArn(policyArn);

        try {
            DeletePolicyResult result = this.awsIamClient.deletePolicy(request);
            // TODO: Log result
            success = true;
        } catch (NoSuchEntityException e) {
            log.error("Failed to policy with ARN: {}", policyArn);
        }

        return success;
    }

    @Override
    public <BE extends BaseIaasPolicy> IaasPolicy add(BE addition) {
        CreatePolicyRequest request =
                new CreatePolicyRequest()
                        .withPolicyName(addition.getName())
                        .withDescription(addition.getDescription())
                        .withPolicyDocument(addition.getDocument());

        CreatePolicyResult result = this.awsIamClient.createPolicy(request);

        IaasPolicy policy = mapper.fromIaasEntity(result.getPolicy());
        policy.setDocument(getDefaultDocument(policy.getName()));

        return policy;
    }

    @Override
    public boolean exists(String policyName) {
        String policyArn = awsMapper.getPolicyArn(policyName);
        GetPolicyRequest request = new GetPolicyRequest().withPolicyArn(policyArn);
        try {
            this.awsIamClient.getPolicy(request);
            return true;
        } catch (NoSuchEntityException e) {
            log.error("No policy found with arn: {}", policyArn);
            return false;
        }
    }

    @Override
    public <UE extends UpdateIaasPolicy> IaasPolicy put(UE policy) {
        if (!exists(policy.getName())) {
            return add(policy);
        } else {
            log.info("Updating the already existing policy. Description value will not be updated");
            return update(policy);
        }
    }

    public <UE extends UpdateIaasPolicy> IaasPolicy update(UE policy) {
        String policyArn = awsMapper.getPolicyArn(policy.getName());
        CreatePolicyVersionRequest request =
                new CreatePolicyVersionRequest()
                        .withPolicyArn(policyArn)
                        .withPolicyDocument(policy.getDocument())
                        .withSetAsDefault(true);

        CreatePolicyVersionResult result = awsIamClient.createPolicyVersion(request);
        // A policy can have max 5 versions. After that the existing versions need to be
        // deleted.
        // This implementation removes all existing versions and retains only 1 at each
        // time.
        deleteNonDefaultPolicyVersions(policy.getName());

        // TODO: Perform a test to make sure that when a new policy version is created,
        // the get
        // policy
        // returns the default document and not the original policy document.
        return get(policy.getName());
    }

    public List<PolicyVersion> getPolicyVersions(String policyName) {
        String policyArn = awsMapper.getPolicyArn(policyName);
        List<PolicyVersion> policyVersions = new ArrayList<>();

        String marker = null;
        do {
            ListPolicyVersionsRequest listRequest =
                    new ListPolicyVersionsRequest().withPolicyArn(policyArn).withMarker(marker);

            ListPolicyVersionsResult listResult = this.awsIamClient.listPolicyVersions(listRequest);

            marker =
                    BooleanUtils.isTrue(listResult.getIsTruncated())
                            ? listResult.getMarker()
                            : null;

            List<PolicyVersion> policyVersionsSubset = listResult.getVersions();
            policyVersions.addAll(policyVersionsSubset);
        } while (marker != null);

        return policyVersions;
    }

    protected void deleteNonDefaultPolicyVersions(String policyName) {
        List<PolicyVersion> versions = getPolicyVersions(policyName);

        String policyArn = awsMapper.getPolicyArn(policyName);
        versions.stream()
                .filter(version -> !version.isDefaultVersion())
                .forEach(version -> deletePolicyVersion(policyArn, version.getVersionId()));
    }

    protected void deletePolicyVersion(String policyArn, String versionId) {
        DeletePolicyVersionRequest request =
                new DeletePolicyVersionRequest().withPolicyArn(policyArn).withVersionId(versionId);

        DeletePolicyVersionResult result = awsIamClient.deletePolicyVersion(request);
    }

    protected String getDefaultDocument(String policyName) {
        return getPolicyVersions(policyName).stream()
                .filter(version -> version.getIsDefaultVersion())
                .findAny()
                .get()
                .getDocument();
    }
}
