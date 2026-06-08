package io.trishul.ai.chat.model;

import io.trishul.model.base.mapper.BaseMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AiChatModelConfigMapper extends
    BaseMapper<AiChatModelConfig, AiChatModelConfigDto, AddAiChatModelConfigDto, UpdateAiChatModelConfigDto> {
  AiChatModelConfigMapper INSTANCE = Mappers.getMapper(AiChatModelConfigMapper.class);

  @Mapping(target = AiChatModelConfig.ATTR_LAST_UPDATED, ignore = true)
  @Mapping(target = AiChatModelConfig.ATTR_CREATED_AT, ignore = true)
  @Mapping(target = AiChatModelConfig.ATTR_VERSION, ignore = true)
  @Mapping(target = AiChatModelConfig.ATTR_NAME, ignore = true)
  @Mapping(target = AiChatModelConfig.ATTR_PROVIDER, ignore = true)
  @Mapping(target = AiChatModelConfig.ATTR_MODEL_NAME, ignore = true)
  @Mapping(target = AiChatModelConfig.ATTR_STREAMING_MODEL_NAME, ignore = true)
  @Mapping(target = AiChatModelConfig.ATTR_API_KEY, ignore = true)
  @Mapping(target = AiChatModelConfig.ATTR_TEMPERATURE, ignore = true)
  @Mapping(target = AiChatModelConfig.ATTR_MAX_TOKENS, ignore = true)
  @Mapping(target = AiChatModelConfig.ATTR_TOP_P, ignore = true)
  @Mapping(target = AiChatModelConfig.ATTR_IS_DEFAULT, ignore = true)
  AiChatModelConfig fromDto(Long id);

  @Override
  @Mapping(target = AiChatModelConfig.ATTR_ID, ignore = true)
  @Mapping(target = AiChatModelConfig.ATTR_LAST_UPDATED, ignore = true)
  @Mapping(target = AiChatModelConfig.ATTR_CREATED_AT, ignore = true)
  @Mapping(target = AiChatModelConfig.ATTR_VERSION, ignore = true)
  AiChatModelConfig fromAddDto(AddAiChatModelConfigDto addDto);

  @Override
  @Mapping(target = AiChatModelConfig.ATTR_LAST_UPDATED, ignore = true)
  @Mapping(target = AiChatModelConfig.ATTR_CREATED_AT, ignore = true)
  AiChatModelConfig fromUpdateDto(UpdateAiChatModelConfigDto updateDto);

  @Override
  AiChatModelConfigDto toDto(AiChatModelConfig entity);
}
