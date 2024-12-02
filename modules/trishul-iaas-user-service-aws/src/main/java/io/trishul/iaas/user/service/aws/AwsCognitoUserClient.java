package io.trishul.iaas.user.service.aws;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProvider;
import com.amazonaws.services.cognitoidp.model.AdminCreateUserRequest;
import com.amazonaws.services.cognitoidp.model.AdminCreateUserResult;
import com.amazonaws.services.cognitoidp.model.AdminDeleteUserRequest;
import com.amazonaws.services.cognitoidp.model.AdminDeleteUserResult;
import com.amazonaws.services.cognitoidp.model.AdminGetUserRequest;
import com.amazonaws.services.cognitoidp.model.AdminGetUserResult;
import com.amazonaws.services.cognitoidp.model.AdminUpdateUserAttributesRequest;
import com.amazonaws.services.cognitoidp.model.AdminUpdateUserAttributesResult;
import com.amazonaws.services.cognitoidp.model.AttributeType;
import com.amazonaws.services.cognitoidp.model.DeliveryMediumType;
import com.amazonaws.services.cognitoidp.model.UserNotFoundException;
import com.amazonaws.services.cognitoidp.model.UserType;

import io.trishul.auth.aws.session.context.CognitoPrincipalContext;
import io.trishul.iaas.client.IaasClient;
import io.trishul.iaas.mapper.IaasEntityMapper;
import io.trishul.iaas.user.model.BaseIaasUser;
import io.trishul.iaas.user.model.IaasUser;
import io.trishul.iaas.user.model.UpdateIaasUser;

public class AwsCognitoUserClient implements IaasClient<String, IaasUser, BaseIaasUser, UpdateIaasUser> {
    private static final Logger log = LoggerFactory.getLogger(AwsCognitoUserClient.class);

    private final AWSCognitoIdentityProvider idp;
    private final IaasEntityMapper<UserType, IaasUser> userTypeMapper;
    private final IaasEntityMapper<AdminGetUserResult, IaasUser> resultMapper;

    private final String userPoolId;

    public AwsCognitoUserClient(AWSCognitoIdentityProvider idp, String userPoolId, IaasEntityMapper<AdminGetUserResult, IaasUser> resultMapper, IaasEntityMapper<UserType, IaasUser> userTypeMapper) {
        this.idp = idp;
        this.userPoolId = userPoolId;
        this.resultMapper = resultMapper;
        this.userTypeMapper = userTypeMapper;
    }

    @Override
    public IaasUser get(String id) {
        IaasUser user = null;

        AdminGetUserRequest req = new AdminGetUserRequest()
                                      .withUsername(id)
                                      .withUserPoolId(userPoolId);
        try {
             AdminGetUserResult result = idp.adminGetUser(req);
             return resultMapper.fromIaasEntity(result);
        } catch (UserNotFoundException e) {
            log.error("Failed to fetch user: {}", id);
        }

        return user;
    }

    @Override
    public <BE extends BaseIaasUser> IaasUser add(BE addition) {
        AttributeType[] attributes = new AttributeType[2];
        attributes[0] = new AttributeType().withName(CognitoPrincipalContext.ATTRIBUTE_EMAIL).withValue(addition.getEmail());
        attributes[1] = new AttributeType().withName(CognitoPrincipalContext.ATTRIBUTE_EMAIL_VERIFIED).withValue("true"); // TODO: Do we need to change this?

        final AdminCreateUserRequest request = new AdminCreateUserRequest().withUserPoolId(userPoolId)
                                                                           .withUsername(addition.getEmail())
                                                                           .withDesiredDeliveryMediums(DeliveryMediumType.EMAIL)
                                                                           .withUserAttributes(attributes);
        final AdminCreateUserResult result = this.idp.adminCreateUser(request);
        return userTypeMapper.fromIaasEntity(result.getUser());
    }

    @Override
    public <UE extends UpdateIaasUser> IaasUser put(UE update) {
        if (!exists(update.getId())) {
            return add(update);
        } else {
            return update(update);
        }
    }

    @Override
    public boolean delete(String id) {
        boolean success = false;
        AdminDeleteUserRequest req = new AdminDeleteUserRequest()
                                         .withUsername(id)
                                         .withUserPoolId(userPoolId);

        try {
            AdminDeleteUserResult result = this.idp.adminDeleteUser(req);
            success = true;
        } catch (UserNotFoundException e) {
            log.error("Failed to delete user: {}", id);
        }

        return success;
    }

    @Override
    public boolean exists(String id) {
        return get(id) != null;
    }

    public <UE extends UpdateIaasUser> IaasUser update(UE update) {
        AttributeType[] attributes = new AttributeType[2];
        attributes[0] = new AttributeType().withName(CognitoPrincipalContext.ATTRIBUTE_EMAIL).withValue(update.getEmail());
        attributes[1] = new AttributeType().withName(CognitoPrincipalContext.ATTRIBUTE_EMAIL_VERIFIED).withValue("true"); // TODO: Do we need to change this?

        AdminUpdateUserAttributesRequest req = new AdminUpdateUserAttributesRequest().withUserPoolId(userPoolId)
                                                                                     .withUsername(update.getEmail())
                                                                                     .withUserAttributes(attributes);

        AdminUpdateUserAttributesResult result = this.idp.adminUpdateUserAttributes(req);

        return get(update.getId());
    }
}
