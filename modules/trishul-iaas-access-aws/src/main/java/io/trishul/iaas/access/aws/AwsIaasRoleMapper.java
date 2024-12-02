package io.trishul.iaas.access.aws;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.amazonaws.services.identitymanagement.model.Role;

import io.trishul.iaas.access.role.model.IaasRole;
import io.trishul.iaas.mapper.IaasEntityMapper;
import io.trishul.model.mapper.LocalDateTimeMapper;

@Mapper(uses = LocalDateTimeMapper.class)
public interface AwsIaasRoleMapper extends IaasEntityMapper<Role, IaasRole> {
    final AwsIaasRoleMapper INSTANCE = Mappers.getMapper(AwsIaasRoleMapper.class);

    @Override
    @Mapping(ignore = true, target = IaasRole.ATTR_ID)
    @Mapping(source = "roleName", target = IaasRole.ATTR_NAME)
    @Mapping(source = "description", target = IaasRole.ATTR_DESCRIPTION)
    @Mapping(source = "assumeRolePolicyDocument", target = IaasRole.ATTR_ASSUME_POLICY_DOCUMENT)
    @Mapping(source = "arn", target = IaasRole.ATTR_IAAS_RESOURCE_NAME)
    @Mapping(source = "roleId", target = IaasRole.ATTR_IAAS_ID)
    @Mapping(source = "roleLastUsed.lastUsedDate", target = IaasRole.ATTR_LAST_USED)
    @Mapping(source = "createDate", target = IaasRole.ATTR_CREATED_AT)
    @Mapping(ignore = true, target = IaasRole.ATTR_LAST_UPDATED)
    IaasRole fromIaasEntity(Role role);
}
