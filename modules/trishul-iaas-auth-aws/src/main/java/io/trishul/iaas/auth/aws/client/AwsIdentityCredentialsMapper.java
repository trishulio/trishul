package io.trishul.iaas.auth.aws.client;

import com.amazonaws.services.cognitoidentity.model.Credentials;
import io.trishul.iaas.auth.session.context.IaasAuthorization;
import io.trishul.iaas.mapper.IaasEntityMapper;
import io.trishul.model.mapper.LocalDateTimeMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(uses = LocalDateTimeMapper.class)
public interface AwsIdentityCredentialsMapper
    extends IaasEntityMapper<Credentials, IaasAuthorization> {
  final AwsIdentityCredentialsMapper INSTANCE
      = Mappers.getMapper(AwsIdentityCredentialsMapper.class);

  @Override
  @Mapping(ignore = true, target = IaasAuthorization.ATTR_ID) // AccessKeyId is the ID
  @Mapping(source = "accessKeyId", target = IaasAuthorization.ATTR_ACCESS_KEY_ID)
  @Mapping(source = "secretKey", target = IaasAuthorization.ATTR_ACCESS_SECRET_KEY)
  @Mapping(source = "sessionToken", target = IaasAuthorization.ATTR_SESSION_TOKEN)
  @Mapping(source = "expiration", target = IaasAuthorization.ATTR_EXPIRATION)
  IaasAuthorization fromIaasEntity(Credentials credentials);
}
