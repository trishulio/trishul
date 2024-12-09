package io.trishul.iaas.tenant.idp.service.aws.cognito.client;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProvider;
import com.amazonaws.services.cognitoidp.model.AdminAddUserToGroupRequest;
import com.amazonaws.services.cognitoidp.model.AdminAddUserToGroupResult;
import com.amazonaws.services.cognitoidp.model.AdminListGroupsForUserRequest;
import com.amazonaws.services.cognitoidp.model.AdminListGroupsForUserResult;
import com.amazonaws.services.cognitoidp.model.AdminRemoveUserFromGroupRequest;
import com.amazonaws.services.cognitoidp.model.AdminRemoveUserFromGroupResult;
import com.amazonaws.services.cognitoidp.model.GroupType;
import com.amazonaws.services.cognitoidp.model.ResourceNotFoundException;

import io.trishul.iaas.client.IaasClient;
import io.trishul.iaas.user.model.BaseIaasUserTenantMembership;
import io.trishul.iaas.user.model.IaasUser;
import io.trishul.iaas.user.model.IaasUserTenantMembership;
import io.trishul.iaas.user.model.IaasUserTenantMembershipId;
import io.trishul.iaas.user.model.UpdateIaasUserTenantMembership;

public class AwsIaasUserTenantMembershipClient implements IaasClient<IaasUserTenantMembershipId, IaasUserTenantMembership, BaseIaasUserTenantMembership, UpdateIaasUserTenantMembership> {
    private static final Logger log = LoggerFactory.getLogger(AwsIaasUserTenantMembershipClient.class);

    private final AWSCognitoIdentityProvider idp;
    private final String userPoolId;

    public AwsIaasUserTenantMembershipClient(AWSCognitoIdentityProvider idp, String userPoolId) {
        this.idp = idp;
        this.userPoolId = userPoolId;
    }

    @Override
    public IaasUserTenantMembership get(IaasUserTenantMembershipId id) {
        IaasUserTenantMembership membership = null;
        GroupType group = getGroup(id.getUserId(), id.getTenantId());
        if (group != null) {
            membership = new IaasUserTenantMembership();

            // We could fetch the user object by injecting the IaasUserClient here but since
            // there's no use-case, a dummy object is assigned with the ID value to save an API call
            membership.setUser(new IaasUser(id.getUserId()));
            membership.setTenantId(id.getTenantId());
        }

        return membership;
    }

    @Override
    public <BE extends BaseIaasUserTenantMembership> IaasUserTenantMembership add(BE addition) {
        AdminAddUserToGroupRequest request = new AdminAddUserToGroupRequest()
                                                 .withUsername(addition.getUser().getId())
                                                 .withGroupName(addition.getTenantId())
                                                 .withUserPoolId(userPoolId);
        AdminAddUserToGroupResult result = this.idp.adminAddUserToGroup(request);

        return (IaasUserTenantMembership) addition;
    }

    @Override
    public <UE extends UpdateIaasUserTenantMembership> IaasUserTenantMembership put(UE update) {
        IaasUserTenantMembership membership = this.get(update.getId());
        if (membership == null) {
            membership = add(update);
        }

        return membership;
    }

    @Override
    public boolean delete(IaasUserTenantMembershipId id) {
        boolean success = false;
        AdminRemoveUserFromGroupRequest request = new AdminRemoveUserFromGroupRequest()
                                                      .withUsername(id.getUserId())
                                                      .withGroupName(id.getTenantId())
                                                      .withUserPoolId(userPoolId);
        try {
            AdminRemoveUserFromGroupResult result = this.idp.adminRemoveUserFromGroup(request);
            success = true;
        } catch (ResourceNotFoundException e) {
            log.error("Failed to remove user: {} from group: {}", id.getUserId(), id.getTenantId());
        }

        return success;
    }

    @Override
    public boolean exists(IaasUserTenantMembershipId id) {
        return getGroup(id.getUserId(), id.getTenantId()) != null;
    }

    private GroupType getGroup(String userName, String groupName) {
        return getGroups(userName).stream()
                                  .filter(group -> group.getGroupName().equalsIgnoreCase(groupName))
                                  .findAny().orElse(null);
    }

    private List<GroupType> getGroups (String userName) {
        List<GroupType> groups = new ArrayList<>();
        String next = null;
        do {
            AdminListGroupsForUserRequest request = new AdminListGroupsForUserRequest()
                    .withUsername(userName)
                    .withNextToken(next)
                    .withUserPoolId(userPoolId);

           AdminListGroupsForUserResult result = idp.adminListGroupsForUser(request);
           next = result.getNextToken();

           groups.addAll(result.getGroups());
        } while (next != null);

        return groups;
    }
}
