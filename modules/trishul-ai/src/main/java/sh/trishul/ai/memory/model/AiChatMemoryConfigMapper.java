package sh.trishul.ai.memory.model;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import sh.trishul.model.base.mapper.BaseMapper;

@Mapper
public interface AiChatMemoryConfigMapper extends
    BaseMapper<AiChatMemoryConfig, AiChatMemoryConfigDto, AddAiChatMemoryConfigDto, UpdateAiChatMemoryConfigDto> {
  AiChatMemoryConfigMapper INSTANCE = Mappers.getMapper(AiChatMemoryConfigMapper.class);

  @Mapping(target = AiChatMemoryConfig.ATTR_LAST_UPDATED, ignore = true)
  @Mapping(target = AiChatMemoryConfig.ATTR_CREATED_AT, ignore = true)
  @Mapping(target = AiChatMemoryConfig.ATTR_VERSION, ignore = true)
  @Mapping(target = AiChatMemoryConfig.ATTR_NAME, ignore = true)
  @Mapping(target = AiChatMemoryConfig.ATTR_STRATEGY, ignore = true)
  @Mapping(target = AiChatMemoryConfig.ATTR_MAX_MESSAGES, ignore = true)
  @Mapping(target = AiChatMemoryConfig.ATTR_MAX_TOKENS, ignore = true)
  @Mapping(target = AiChatMemoryConfig.ATTR_TTL_MINUTES, ignore = true)
  @Mapping(target = AiChatMemoryConfig.ATTR_IS_DEFAULT, ignore = true)
  AiChatMemoryConfig fromDto(Long id);

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
