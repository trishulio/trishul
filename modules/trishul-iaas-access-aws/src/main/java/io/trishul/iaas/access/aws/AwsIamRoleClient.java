package io.trishul.iaas.access.aws;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import com.amazonaws.services.identitymanagement.model.UpdateAssumeRolePolicyResult;
import com.amazonaws.services.identitymanagement.model.UpdateRoleRequest;
import com.amazonaws.services.identitymanagement.model.UpdateRoleResult;
import io.trishul.iaas.access.role.model.BaseIaasRole;
import io.trishul.iaas.access.role.model.IaasRole;
import io.trishul.iaas.access.role.model.UpdateIaasRole;
import io.trishul.iaas.client.IaasClient;
import io.trishul.iaas.mapper.IaasEntityMapper;

public class AwsIamRoleClient
    implements IaasClient<String, IaasRole, BaseIaasRole<?>, UpdateIaasRole<?>> {
  private static final Logger log = LoggerFactory.getLogger(AwsIamRoleClient.class);

  private final AmazonIdentityManagement awsIamClient;
  private final IaasEntityMapper<Role, IaasRole> mapper;

  public AwsIamRoleClient(AmazonIdentityManagement awsIamClient,
      IaasEntityMapper<Role, IaasRole> mapper) {
    this.awsIamClient = awsIamClient;
    this.mapper = mapper;
  }

  @Override
  public IaasRole get(String roleName) {
    GetRoleRequest request = new GetRoleRequest().withRoleName(roleName);

    GetRoleResult result = this.awsIamClient.getRole(request);

    return this.mapper.fromIaasEntity(result.getRole());
  }

  @Override
  public boolean delete(String roleName) {
    boolean success = false;
    DeleteRoleRequest request = new DeleteRoleRequest().withRoleName(roleName);
    try {
      DeleteRoleResult result = this.awsIamClient.deleteRole(request);
      log.info("Successfully deleted AWS IAM role. Name: {}, RequestId: {}", roleName,
          result.getSdkResponseMetadata().getRequestId());
      success = true;
    } catch (NoSuchEntityException e) {
    }

    return success;
  }

  @Override
  public <BE extends BaseIaasRole<?>> IaasRole add(BE role) {
    CreateRoleRequest request = new CreateRoleRequest().withRoleName(role.getName())
        .withAssumeRolePolicyDocument(role.getAssumePolicyDocument())
        .withDescription(role.getDescription());

    CreateRoleResult result = this.awsIamClient.createRole(request);

    return mapper.fromIaasEntity(result.getRole());
  }

  @Override
  public boolean exists(String roleName) {
    try {
      get(roleName);
      return true;
    } catch (NoSuchEntityException e) {
      return false;
    }
  }

  @Override
  public <UE extends UpdateIaasRole<?>> IaasRole put(UE role) {
    if (exists(role.getName())) {
      return update(role);
    } else {
      return add(role);
    }
  }

  public <UE extends UpdateIaasRole<?>> IaasRole update(UE role) {
    UpdateRoleRequest request = new UpdateRoleRequest().withRoleName(role.getName())
        .withDescription(role.getDescription());

    UpdateRoleResult result = this.awsIamClient.updateRole(request);
    log.info("Updated AWS IAM role. Name: {}, RequestId: {}", role.getName(),
        result.getSdkResponseMetadata().getRequestId());

    UpdateAssumeRolePolicyRequest policyRequest = new UpdateAssumeRolePolicyRequest()
        .withRoleName(role.getName()).withPolicyDocument(role.getAssumePolicyDocument());
    UpdateAssumeRolePolicyResult policyResult
        = this.awsIamClient.updateAssumeRolePolicy(policyRequest);

    log.info("Updated assume role policy. Name: {}, RequestId: {}", role.getName(),
        policyResult.getSdkResponseMetadata().getRequestId());
    return get(role.getName());
  }
}
