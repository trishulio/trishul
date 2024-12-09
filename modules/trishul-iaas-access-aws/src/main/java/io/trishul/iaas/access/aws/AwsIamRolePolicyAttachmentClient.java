package io.trishul.iaas.access.aws;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutionException;

import org.apache.commons.lang3.BooleanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazonaws.services.identitymanagement.AmazonIdentityManagement;
import com.amazonaws.services.identitymanagement.model.AttachRolePolicyRequest;
import com.amazonaws.services.identitymanagement.model.AttachRolePolicyResult;
import com.amazonaws.services.identitymanagement.model.AttachedPolicy;
import com.amazonaws.services.identitymanagement.model.DetachRolePolicyRequest;
import com.amazonaws.services.identitymanagement.model.DetachRolePolicyResult;
import com.amazonaws.services.identitymanagement.model.ListAttachedRolePoliciesRequest;
import com.amazonaws.services.identitymanagement.model.ListAttachedRolePoliciesResult;
import com.amazonaws.services.identitymanagement.model.NoSuchEntityException;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import io.trishul.iaas.access.policy.model.IaasPolicy;
import io.trishul.iaas.access.role.attachment.policy.BaseIaasRolePolicyAttachment;
import io.trishul.iaas.access.role.attachment.policy.IaasRolePolicyAttachmentId;
import io.trishul.iaas.access.role.attachment.policy.IaasRolePolicyAttachment;
import io.trishul.iaas.access.role.attachment.policy.UpdateIaasRolePolicyAttachment;
import io.trishul.iaas.access.role.model.IaasRole;
import io.trishul.iaas.client.IaasClient;

public class AwsIamRolePolicyAttachmentClient implements IaasClient<IaasRolePolicyAttachmentId, IaasRolePolicyAttachment, BaseIaasRolePolicyAttachment, UpdateIaasRolePolicyAttachment> {
    private static final Logger log = LoggerFactory.getLogger(AwsIamRolePolicyAttachmentClient.class);

    private final InheritableThreadLocal<LoadingCache<String, Set<String>>> attachedPolicyNameLocalCache;
    private final AmazonIdentityManagement awsClient;
    private final AwsArnMapper arnMapper;

    public AwsIamRolePolicyAttachmentClient(AmazonIdentityManagement awsIamClient, AwsArnMapper arnMapper) {
        this.awsClient = awsIamClient;
        this.arnMapper = arnMapper;

        this.attachedPolicyNameLocalCache = new InheritableThreadLocal<>();
    }

    @Override
    public IaasRolePolicyAttachment get(IaasRolePolicyAttachmentId id) {
        IaasRolePolicyAttachment attachment = null;
        try {
            Set<String> attachedPolicyNames = getCache().get(id.getRoleId());

            if (attachedPolicyNames.contains(id.getPolicyId())) {
                IaasRole role = new IaasRole(id.getRoleId());
                IaasPolicy policy = new IaasPolicy(id.getPolicyId());

                // Note: Attachment with Role and Policy containing IDs are returned.
                // Ideally, we should fetch both the objects here but there aren't any
                // use-cases for that yet so not worth it to add 2 extra api calls.
                attachment = new IaasRolePolicyAttachment(role, policy);
            }

            return attachment;

        } catch (ExecutionException e) {
            throw new RuntimeException("Failed to fetch all the attachedPolicies because " + e.getMessage(), e);
        }
    }

    @Override
    public <BE extends BaseIaasRolePolicyAttachment> IaasRolePolicyAttachment add(BE attachment) {
        String policyArn = this.arnMapper.getPolicyArn(attachment.getIaasPolicy().getId());

        AttachRolePolicyRequest request = new AttachRolePolicyRequest()
                                            .withPolicyArn(policyArn)
                                            .withRoleName(attachment.getIaasRole().getId());

        AttachRolePolicyResult result = this.awsClient.attachRolePolicy(request);

        getCache().invalidate(attachment.getIaasRole().getId());

        return (IaasRolePolicyAttachment) attachment;
    }

    @Override
    public <UE extends UpdateIaasRolePolicyAttachment> IaasRolePolicyAttachment put(UE update) {
        IaasRolePolicyAttachment attachment = get(update.getId());
        if (attachment == null) {
            attachment = add(update);
        }
        return attachment;
    }

    @Override
    public boolean delete(IaasRolePolicyAttachmentId id) {
        String policyArn = this.arnMapper.getPolicyArn(id.getPolicyId());

        DetachRolePolicyRequest request = new DetachRolePolicyRequest()
                                            .withPolicyArn(policyArn)
                                            .withRoleName(id.getRoleId());
        try {
            DetachRolePolicyResult result = this.awsClient.detachRolePolicy(request);
            getCache().invalidate(id.getRoleId());
            return true;
        } catch(NoSuchEntityException e) {
            log.error("Failed to delete attachment with role: '{}' and policy: '{}'", id.getRoleId(), policyArn);
            return false;
        }
    }

    @Override
    public boolean exists(IaasRolePolicyAttachmentId id) {
        return get(id) != null;
    }

    private LoadingCache<String, Set<String>> getCache() {
        LoadingCache<String, Set<String>> cache = this.attachedPolicyNameLocalCache.get();
        if (cache == null) {
            cache = CacheBuilder.newBuilder().build(new CacheLoader<String, Set<String>>(){
                @Override
                public Set<String> load(String roleName) throws Exception {
                    Set<String> allPolicyNames = new HashSet<>();
                    String marker = null;
                    do {
                        ListAttachedRolePoliciesRequest request = new ListAttachedRolePoliciesRequest()
                                                                  .withRoleName(roleName)
                                                                  .withMarker(marker);
                        ListAttachedRolePoliciesResult result = null;
                        try {
                            result = awsClient.listAttachedRolePolicies(request);
                        } catch (NoSuchEntityException e) {
                            marker = null;
                            continue;
                        }

                        marker = BooleanUtils.isTrue(result.isTruncated()) ? result.getMarker() : null;

                        List<AttachedPolicy> policies = result.getAttachedPolicies();
                        policies.stream().map(policy -> policy.getPolicyName()).forEach(allPolicyNames::add);
                    } while (marker != null);
                    return allPolicyNames;
                }
            });

            attachedPolicyNameLocalCache.set(cache);
        }

        return cache;
    }
}
