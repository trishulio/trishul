package io.trishul.iaas.user.aws.model;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.util.CollectionUtils;

import com.amazonaws.services.cognitoidp.model.AttributeType;
import com.amazonaws.services.cognitoidp.model.UserType;

import io.trishul.auth.aws.session.context.CognitoPrincipalContext;
import io.trishul.iaas.mapper.IaasEntityMapper;
import io.trishul.iaas.user.model.IaasUser;
import io.trishul.model.mapper.LocalDateTimeMapper;

@Mapper
public interface AwsCognitoUserMapper  extends IaasEntityMapper<UserType, IaasUser> {
    final AwsCognitoUserMapper INSTANCE = Mappers.getMapper(AwsCognitoUserMapper.class);

    @Override
    default IaasUser fromIaasEntity(UserType userType) {
        IaasUser iaasUser = null;

        if (userType != null) {
            iaasUser = new IaasUser();
            iaasUser.setId(userType.getUsername());
            iaasUser.setCreatedAt(LocalDateTimeMapper.INSTANCE.fromUtilDate(userType.getUserCreateDate()));
            iaasUser.setLastUpdated(LocalDateTimeMapper.INSTANCE.fromUtilDate(userType.getUserLastModifiedDate()));
            List<AttributeType> attributes = userType.getAttributes();
            if (!CollectionUtils.isEmpty(attributes)) {
                for(AttributeType attr: attributes) {
                    if (CognitoPrincipalContext.ATTRIBUTE_EMAIL.equalsIgnoreCase(attr.getName())) {
                        iaasUser.setEmail(attr.getValue());
                    }
                }
            }

            iaasUser.setPhoneNumber(null);
        }

        return iaasUser;
    }
}