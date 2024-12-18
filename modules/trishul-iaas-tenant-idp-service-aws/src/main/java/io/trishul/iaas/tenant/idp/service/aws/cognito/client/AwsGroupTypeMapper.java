package io.trishul.iaas.tenant.idp.service.aws.cognito.client;

import com.amazonaws.services.cognitoidp.model.GroupType;
import io.trishul.iaas.idp.tenant.model.IaasIdpTenant;
import io.trishul.iaas.mapper.IaasEntityMapper;
import io.trishul.model.mapper.LocalDateTimeMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(uses = {LocalDateTimeMapper.class})
public interface AwsGroupTypeMapper extends IaasEntityMapper<GroupType, IaasIdpTenant> {
    final AwsGroupTypeMapper INSTANCE = Mappers.getMapper(AwsGroupTypeMapper.class);

    @Override
    @Mapping(target = IaasIdpTenant.ATTR_CREATED_AT, source = "creationDate")
    @Mapping(target = IaasIdpTenant.ATTR_NAME, source = "groupName")
    @Mapping(target = IaasIdpTenant.ATTR_ID, ignore = true) // ID is same as name
    @Mapping(target = IaasIdpTenant.ATTR_DESCRIPTION, source = "description")
    @Mapping(target = IaasIdpTenant.ATTR_LAST_UPDATED, source = "lastModifiedDate")
    @Mapping(target = IaasIdpTenant.ATTR_VERSION, ignore = true)
    @Mapping(
            target = IaasIdpTenant.ATTR_IAAS_ROLE,
            ignore = true) // TODO: If there's a use-case implement a mapper
    // from Role ARN to
    // IaasRole
    IaasIdpTenant fromIaasEntity(GroupType entity);
}
