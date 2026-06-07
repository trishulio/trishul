package io.trishul.ai.memory.model;

import io.trishul.model.base.mapper.BaseMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AiChatMemoryConfigMapper extends
    BaseMapper<AiChatMemoryConfig, AiChatMemoryConfigDto, AddAiChatMemoryConfigDto, UpdateAiChatMemoryConfigDto> {
  AiChatMemoryConfigMapper INSTANCE = Mappers.getMapper(AiChatMemoryConfigMapper.class);

  @Override
  @Mapping(target = AiChatMemoryConfig.ATTR_ID, ignore = true)
  @Mapping(target = AiChatMemoryConfig.ATTR_LAST_UPDATED, ignore = true)
  @Mapping(target = AiChatMemoryConfig.ATTR_CREATED_AT, ignore = true)
  @Mapping(target = AiChatMemoryConfig.ATTR_VERSION, ignore = true)
  AiChatMemoryConfig fromAddDto(AddAiChatMemoryConfigDto addDto);

  @Override
  @Mapping(target = AiChatMemoryConfig.ATTR_LAST_UPDATED, ignore = true)
  @Mapping(target = AiChatMemoryConfig.ATTR_CREATED_AT, ignore = true)
  AiChatMemoryConfig fromUpdateDto(UpdateAiChatMemoryConfigDto updateDto);

  @Override
  AiChatMemoryConfigDto toDto(AiChatMemoryConfig entity);
}
