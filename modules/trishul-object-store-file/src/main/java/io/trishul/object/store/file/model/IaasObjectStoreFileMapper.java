package io.trishul.object.store.file.model;

import io.trishul.model.base.mapper.BaseMapper;
import io.trishul.object.store.file.model.dto.AddIaasObjectStoreFileDto;
import io.trishul.object.store.file.model.dto.IaasObjectStoreFileDto;
import io.trishul.object.store.file.model.dto.UpdateIaasObjectStoreFileDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface IaasObjectStoreFileMapper extends
    BaseMapper<IaasObjectStoreFile, IaasObjectStoreFileDto, AddIaasObjectStoreFileDto, UpdateIaasObjectStoreFileDto> {
  final IaasObjectStoreFileMapper INSTANCE = Mappers.getMapper(IaasObjectStoreFileMapper.class);

  @Override
  @Mapping(target = Identified.ATTR_ID, ignore = true)
  @Mapping(target = BaseIaasObjectStoreFile.ATTR_FILE_KEY, ignore = true)
  @Mapping(target = BaseIaasObjectStoreFile.ATTR_FILE_URL, ignore = true)
  @Mapping(target = BaseIaasObjectStoreFile.ATTR_EXPIRATION, ignore = true)
  @Mapping(target = BaseIaasObjectStoreFile.ATTR_MIN_VALID_UNTIL, source = "minValidUntil")
  IaasObjectStoreFile fromAddDto(AddIaasObjectStoreFileDto dto);

  @Override
  @Mapping(target = Identified.ATTR_ID, ignore = true)
  @Mapping(target = BaseIaasObjectStoreFile.ATTR_FILE_URL, ignore = true)
  @Mapping(target = BaseIaasObjectStoreFile.ATTR_EXPIRATION, ignore = true)
  @Mapping(target = BaseIaasObjectStoreFile.ATTR_MIN_VALID_UNTIL, source = "minValidUntil")
  IaasObjectStoreFile fromUpdateDto(UpdateIaasObjectStoreFileDto dto);

  @Override
  @Mapping(target = "fileKey", source = BaseIaasObjectStoreFile.ATTR_FILE_KEY)
  @Mapping(target = "fileUrl", source = BaseIaasObjectStoreFile.ATTR_FILE_URL)
  @Mapping(target = "expiration", source = BaseIaasObjectStoreFile.ATTR_EXPIRATION)
  IaasObjectStoreFileDto toDto(IaasObjectStoreFile e);
}
