package io.trishul.iaas.access.aws;

import com.amazonaws.services.identitymanagement.model.Policy;
import io.trishul.iaas.access.policy.model.IaasPolicy;
import io.trishul.iaas.mapper.IaasEntityMapper;
import io.trishul.model.mapper.LocalDateTimeMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(uses = LocalDateTimeMapper.class)
public interface AwsIaasPolicyMapper extends IaasEntityMapper<Policy, IaasPolicy> {
  final AwsIaasPolicyMapper INSTANCE = Mappers.getMapper(AwsIaasPolicyMapper.class);

  @Override
  @Mapping(ignore = true, target = IaasPolicy.ATTR_ID) // ID is same as name
  @Mapping(ignore = true, target = IaasPolicy.ATTR_DOCUMENT) // Policy does not contain document
  @Mapping(source = "policyName", target = IaasPolicy.ATTR_NAME)
  @Mapping(source = "description", target = IaasPolicy.ATTR_DESCRIPTION)
  @Mapping(source = "policyId", target = IaasPolicy.ATTR_IAAS_ID)
  @Mapping(source = "arn", target = IaasPolicy.ATTR_IAAS_RESOURCE_NAME)
  @Mapping(source = "createDate", target = IaasPolicy.ATTR_CREATED_AT)
  @Mapping(source = "updateDate", target = IaasPolicy.ATTR_LAST_UPDATED)
  IaasPolicy fromIaasEntity(Policy iamPolicy);
}
