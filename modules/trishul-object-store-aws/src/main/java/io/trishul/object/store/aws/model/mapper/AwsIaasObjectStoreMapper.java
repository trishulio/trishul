package io.trishul.object.store.aws.model.mapper;

import com.amazonaws.services.s3.model.Bucket;
import io.trishul.iaas.mapper.IaasEntityMapper;
import io.trishul.model.mapper.LocalDateTimeMapper;
import io.trishul.object.store.model.IaasObjectStore;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(uses = LocalDateTimeMapper.class)
public interface AwsIaasObjectStoreMapper extends IaasEntityMapper<Bucket, IaasObjectStore> {
  final AwsIaasObjectStoreMapper INSTANCE = Mappers.getMapper(AwsIaasObjectStoreMapper.class);

  @Override
  @Mapping(ignore = true, target = IaasObjectStore.ATTR_ID) // Name is the ID
  @Mapping(source = "name", target = IaasObjectStore.ATTR_NAME)
  @Mapping(source = "creationDate", target = IaasObjectStore.ATTR_CREATED_AT)
  @Mapping(ignore = true, target = IaasObjectStore.ATTR_LAST_UPDATED)
  IaasObjectStore fromIaasEntity(Bucket bucket);
}
