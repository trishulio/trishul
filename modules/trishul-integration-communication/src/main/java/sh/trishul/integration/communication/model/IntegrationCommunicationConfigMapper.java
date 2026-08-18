package sh.trishul.integration.communication.model;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import sh.trishul.integration.model.IntegrationMapper;
import sh.trishul.model.base.mapper.BaseMapper;
import sh.trishul.model.mapper.DeleteResultMapper;

@Mapper(uses = {IntegrationMapper.class, DeleteResultMapper.class})
public interface IntegrationCommunicationConfigMapper extends
    BaseMapper<IntegrationCommunicationConfig, IntegrationCommunicationConfigDto, AddIntegrationCommunicationConfigDto, UpdateIntegrationCommunicationConfigDto> {
  IntegrationCommunicationConfigMapper INSTANCE
      = Mappers.getMapper(IntegrationCommunicationConfigMapper.class);

  @Mapping(target = IntegrationCommunicationConfig.ATTR_LAST_UPDATED, ignore = true)
  @Mapping(target = IntegrationCommunicationConfig.ATTR_CREATED_AT, ignore = true)
  @Mapping(target = IntegrationCommunicationConfig.ATTR_VERSION, ignore = true)
  @Mapping(target = IntegrationCommunicationConfig.ATTR_INTEGRATION, ignore = true)
  @Mapping(target = IntegrationCommunicationConfig.ATTR_CHANNEL_TYPE, ignore = true)
  @Mapping(target = IntegrationCommunicationConfig.ATTR_CHANNEL_ADDRESS, ignore = true)
  @Mapping(target = IntegrationCommunicationConfig.ATTR_DEFAULT_FROM, ignore = true)
  @Mapping(target = IntegrationCommunicationConfig.ATTR_ENABLED, ignore = true)
  IntegrationCommunicationConfig fromDto(Long id);

  @Override
  @Mapping(target = IntegrationCommunicationConfig.ATTR_ID, ignore = true)
  @Mapping(target = IntegrationCommunicationConfig.ATTR_LAST_UPDATED, ignore = true)
  @Mapping(target = IntegrationCommunicationConfig.ATTR_CREATED_AT, ignore = true)
  @Mapping(target = IntegrationCommunicationConfig.ATTR_VERSION, ignore = true)
  @Mapping(target = IntegrationCommunicationConfig.ATTR_INTEGRATION, source = "integrationId")
  IntegrationCommunicationConfig fromAddDto(AddIntegrationCommunicationConfigDto addDto);

  @Override
  @Mapping(target = IntegrationCommunicationConfig.ATTR_LAST_UPDATED, ignore = true)
  @Mapping(target = IntegrationCommunicationConfig.ATTR_CREATED_AT, ignore = true)
  @Mapping(target = IntegrationCommunicationConfig.ATTR_INTEGRATION, source = "integrationId")
  IntegrationCommunicationConfig fromUpdateDto(UpdateIntegrationCommunicationConfigDto updateDto);

  @Override
  IntegrationCommunicationConfigDto toDto(IntegrationCommunicationConfig config);
}
