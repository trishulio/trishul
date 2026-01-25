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
  @Mapping(ignore = true, target = Identified.ATTR_ID) // ID is same as name
  @Mapping(ignore = true, target = BaseIaasPolicy.ATTR_DOCUMENT) // Policy does not contain document
  @Mapping(source = "policyName", target = BaseIaasPolicy.ATTR_NAME)
  @Mapping(source = "description", target = BaseIaasPolicy.ATTR_DESCRIPTION)
  @Mapping(source = "policyId", target = BaseIaasPolicy.ATTR_IAAS_ID)
  @Mapping(source = "arn", target = BaseIaasPolicy.ATTR_IAAS_RESOURCE_NAME)
  @Mapping(source = "createDate", target = Audited.ATTR_CREATED_AT)
  @Mapping(source = "updateDate", target = Audited.ATTR_LAST_UPDATED)
  IaasPolicy fromIaasEntity(Policy iamPolicy);
}
