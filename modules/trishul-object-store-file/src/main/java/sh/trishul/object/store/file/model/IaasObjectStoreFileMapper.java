package sh.trishul.object.store.file.model;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import sh.trishul.model.base.mapper.BaseMapper;
import sh.trishul.object.store.file.model.dto.AddIaasObjectStoreFileDto;
import sh.trishul.object.store.file.model.dto.IaasObjectStoreFileDto;
import sh.trishul.object.store.file.model.dto.UpdateIaasObjectStoreFileDto;

@Mapper
public interface IaasObjectStoreFileMapper extends
    BaseMapper<IaasObjectStoreFile, IaasObjectStoreFileDto, AddIaasObjectStoreFileDto, UpdateIaasObjectStoreFileDto> {
  final IaasObjectStoreFileMapper INSTANCE = Mappers.getMapper(IaasObjectStoreFileMapper.class);

  @Override
  @Mapping(target = IaasObjectStoreFile.ATTR_ID, ignore = true)
  @Mapping(target = IaasObjectStoreFile.ATTR_FILE_KEY, ignore = true)
  @Mapping(target = IaasObjectStoreFile.ATTR_FILE_URL, ignore = true)
  @Mapping(target = IaasObjectStoreFile.ATTR_EXPIRATION, ignore = true)
  @Mapping(target = IaasObjectStoreFile.ATTR_MIN_VALID_UNTIL, source = "minValidUntil")
  IaasObjectStoreFile fromAddDto(AddIaasObjectStoreFileDto dto);

  @Override
  @Mapping(target = IaasObjectStoreFile.ATTR_ID, ignore = true)
  @Mapping(target = IaasObjectStoreFile.ATTR_FILE_URL, ignore = true)
  @Mapping(target = IaasObjectStoreFile.ATTR_EXPIRATION, ignore = true)
  @Mapping(target = IaasObjectStoreFile.ATTR_MIN_VALID_UNTIL, source = "minValidUntil")
  IaasObjectStoreFile fromUpdateDto(UpdateIaasObjectStoreFileDto dto);

  @Override
  IaasObjectStoreFileDto toDto(IaasObjectStoreFile e);
}
