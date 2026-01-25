package io.trishul.iaas.access.aws;

import com.amazonaws.services.identitymanagement.model.Role;
import io.trishul.iaas.access.role.model.IaasRole;
import io.trishul.iaas.mapper.IaasEntityMapper;
import io.trishul.model.mapper.LocalDateTimeMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(uses = LocalDateTimeMapper.class)
public interface AwsIaasRoleMapper extends IaasEntityMapper<Role, IaasRole> {
  final AwsIaasRoleMapper INSTANCE = Mappers.getMapper(AwsIaasRoleMapper.class);

  @Override
  @Mapping(ignore = true, target = Identified.ATTR_ID)
  @Mapping(source = "roleName", target = BaseIaasRole.ATTR_NAME)
  @Mapping(source = "description", target = BaseIaasRole.ATTR_DESCRIPTION)
  @Mapping(source = "assumeRolePolicyDocument", target = BaseIaasRole.ATTR_ASSUME_POLICY_DOCUMENT)
  @Mapping(source = "arn", target = BaseIaasRole.ATTR_IAAS_RESOURCE_NAME)
  @Mapping(source = "roleId", target = BaseIaasRole.ATTR_IAAS_ID)
  @Mapping(source = "roleLastUsed.lastUsedDate", target = BaseIaasRole.ATTR_LAST_USED)
  @Mapping(source = "createDate", target = Audited.ATTR_CREATED_AT)
  @Mapping(ignore = true, target = Audited.ATTR_LAST_UPDATED)
  IaasRole fromIaasEntity(Role role);
}
