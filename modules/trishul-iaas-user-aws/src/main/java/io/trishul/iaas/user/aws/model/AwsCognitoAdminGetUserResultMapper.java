package io.trishul.iaas.user.aws.model;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.util.CollectionUtils;

import com.amazonaws.services.cognitoidp.model.AdminGetUserResult;
import com.amazonaws.services.cognitoidp.model.AttributeType;

import io.trishul.auth.aws.session.context.CognitoPrincipalContext;
import io.trishul.iaas.mapper.IaasEntityMapper;
import io.trishul.iaas.user.model.IaasUser;
import io.trishul.model.mapper.LocalDateTimeMapper;

@Mapper(uses = { LocalDateTimeMapper.class })
public interface AwsCognitoAdminGetUserResultMapper extends IaasEntityMapper<AdminGetUserResult, IaasUser> {
    final AwsCognitoAdminGetUserResultMapper INSTANCE = Mappers.getMapper(AwsCognitoAdminGetUserResultMapper.class);

    @Override
    default IaasUser fromIaasEntity(AdminGetUserResult result) {
        IaasUser iaasUser = null;

        if (result != null) {
            iaasUser = new IaasUser();
            iaasUser.setId(result.getUsername());
            iaasUser.setCreatedAt(LocalDateTimeMapper.INSTANCE.fromUtilDate(result.getUserCreateDate()));
            iaasUser.setLastUpdated(LocalDateTimeMapper.INSTANCE.fromUtilDate(result.getUserLastModifiedDate()));

            List<AttributeType> attributes = result.getUserAttributes();
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
