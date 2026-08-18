package sh.trishul.iaas.user.aws.model;

import com.amazonaws.services.cognitoidp.model.AdminGetUserResult;
import com.amazonaws.services.cognitoidp.model.AttributeType;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.util.CollectionUtils;
import sh.trishul.auth.aws.session.context.CognitoPrincipalContext;
import sh.trishul.iaas.mapper.IaasEntityMapper;
import sh.trishul.iaas.user.model.IaasUser;
import sh.trishul.model.mapper.DeleteResultMapper;
import sh.trishul.model.mapper.LocalDateTimeMapper;

@Mapper(uses = {LocalDateTimeMapper.class, DeleteResultMapper.class})
public interface AwsCognitoAdminGetUserResultMapper
    extends IaasEntityMapper<AdminGetUserResult, IaasUser> {
  final AwsCognitoAdminGetUserResultMapper INSTANCE
      = Mappers.getMapper(AwsCognitoAdminGetUserResultMapper.class);

  @Override
  default IaasUser fromIaasEntity(AdminGetUserResult result) {
    IaasUser iaasUser = null;

    if (result != null) {
      iaasUser = new IaasUser();
      iaasUser.setId(result.getUsername());
      iaasUser.setCreatedAt(LocalDateTimeMapper.INSTANCE.fromUtilDate(result.getUserCreateDate()));
      iaasUser.setLastUpdated(
          LocalDateTimeMapper.INSTANCE.fromUtilDate(result.getUserLastModifiedDate()));

      List<AttributeType> attributes = result.getUserAttributes();
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
