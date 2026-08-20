package sh.trishul.iaas.user.aws.model;

import com.amazonaws.services.cognitoidp.model.AttributeType;
import com.amazonaws.services.cognitoidp.model.UserType;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.util.CollectionUtils;
import sh.trishul.auth.aws.session.context.CognitoPrincipalContext;
import sh.trishul.iaas.mapper.IaasEntityMapper;
import sh.trishul.iaas.user.model.IaasUser;
import sh.trishul.model.mapper.LocalDateTimeMapper;

@Mapper
public interface AwsCognitoUserMapper extends IaasEntityMapper<UserType, IaasUser> {
  AwsCognitoUserMapper INSTANCE = Mappers.getMapper(AwsCognitoUserMapper.class);

  @Override
  default IaasUser fromIaasEntity(UserType userType) {
    IaasUser iaasUser = null;

    if (userType != null) {
      iaasUser = new IaasUser();
      iaasUser.setId(userType.getUsername());
      iaasUser.setUserName(userType.getUsername());
      iaasUser
          .setCreatedAt(LocalDateTimeMapper.INSTANCE.fromUtilDate(userType.getUserCreateDate()));
      iaasUser.setLastUpdated(
          LocalDateTimeMapper.INSTANCE.fromUtilDate(userType.getUserLastModifiedDate()));
      List<AttributeType> attributes = userType.getAttributes();
      if (!CollectionUtils.isEmpty(attributes)) {
        for (AttributeType attr : attributes) {
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
