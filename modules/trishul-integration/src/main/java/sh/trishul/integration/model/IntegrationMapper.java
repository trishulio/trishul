package sh.trishul.integration.model;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import sh.trishul.model.base.mapper.BaseMapper;

@Mapper
public interface IntegrationMapper
    extends BaseMapper<Integration, IntegrationDto, AddIntegrationDto, UpdateIntegrationDto> {
  IntegrationMapper INSTANCE = Mappers.getMapper(IntegrationMapper.class);

  @Mapping(target = Integration.ATTR_LAST_UPDATED, ignore = true)
  @Mapping(target = Integration.ATTR_CREATED_AT, ignore = true)
  @Mapping(target = Integration.ATTR_VERSION, ignore = true)
  @Mapping(target = Integration.ATTR_NAME, ignore = true)
  @Mapping(target = Integration.ATTR_TYPE, ignore = true)
  @Mapping(target = Integration.ATTR_PROVIDER, ignore = true)
  @Mapping(target = Integration.ATTR_STATUS, ignore = true)
  @Mapping(target = Integration.ATTR_CONFIGURATION, ignore = true)
  Integration fromDto(Long id);

  @Override
  @Mapping(target = Integration.ATTR_ID, ignore = true)
  @Mapping(target = Integration.ATTR_LAST_UPDATED, ignore = true)
  @Mapping(target = Integration.ATTR_CREATED_AT, ignore = true)
  @Mapping(target = Integration.ATTR_VERSION, ignore = true)
  Integration fromAddDto(AddIntegrationDto addDto);

  @Override
  @Mapping(target = Integration.ATTR_LAST_UPDATED, ignore = true)
  @Mapping(target = Integration.ATTR_CREATED_AT, ignore = true)
  Integration fromUpdateDto(UpdateIntegrationDto updateDto);

  @Override
  IntegrationDto toDto(Integration integration);
}
