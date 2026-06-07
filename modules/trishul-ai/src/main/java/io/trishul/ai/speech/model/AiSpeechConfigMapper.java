package io.trishul.ai.speech.model;

import io.trishul.model.base.mapper.BaseMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AiSpeechConfigMapper extends
    BaseMapper<AiSpeechConfig, AiSpeechConfigDto, AddAiSpeechConfigDto, UpdateAiSpeechConfigDto> {
  AiSpeechConfigMapper INSTANCE = Mappers.getMapper(AiSpeechConfigMapper.class);

  @Override
  @Mapping(target = AiSpeechConfig.ATTR_ID, ignore = true)
  @Mapping(target = AiSpeechConfig.ATTR_LAST_UPDATED, ignore = true)
  @Mapping(target = AiSpeechConfig.ATTR_CREATED_AT, ignore = true)
  @Mapping(target = AiSpeechConfig.ATTR_VERSION, ignore = true)
  AiSpeechConfig fromAddDto(AddAiSpeechConfigDto addDto);

  @Override
  @Mapping(target = AiSpeechConfig.ATTR_LAST_UPDATED, ignore = true)
  @Mapping(target = AiSpeechConfig.ATTR_CREATED_AT, ignore = true)
  AiSpeechConfig fromUpdateDto(UpdateAiSpeechConfigDto updateDto);

  @Override
  AiSpeechConfigDto toDto(AiSpeechConfig entity);
}
