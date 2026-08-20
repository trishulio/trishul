package sh.trishul.object.store.aws.model.mapper;

import com.amazonaws.services.s3.model.Bucket;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import sh.trishul.iaas.mapper.IaasEntityMapper;
import sh.trishul.model.mapper.DeleteResultMapper;
import sh.trishul.model.mapper.LocalDateTimeMapper;
import sh.trishul.object.store.model.IaasObjectStore;

@Mapper(uses = {LocalDateTimeMapper.class, DeleteResultMapper.class})
public interface AwsIaasObjectStoreMapper extends IaasEntityMapper<Bucket, IaasObjectStore> {
  AwsIaasObjectStoreMapper INSTANCE = Mappers.getMapper(AwsIaasObjectStoreMapper.class);

  @Override
  @Mapping(ignore = true, target = IaasObjectStore.ATTR_ID) // Name is the ID
  @Mapping(source = "name", target = IaasObjectStore.ATTR_NAME)
  @Mapping(source = "creationDate", target = IaasObjectStore.ATTR_CREATED_AT)
  @Mapping(ignore = true, target = IaasObjectStore.ATTR_LAST_UPDATED)
  @Mapping(ignore = true, target = IaasObjectStore.ATTR_VERSION)
  IaasObjectStore fromIaasEntity(Bucket bucket);
}
