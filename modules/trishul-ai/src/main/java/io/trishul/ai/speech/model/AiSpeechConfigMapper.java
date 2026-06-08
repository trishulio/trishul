package io.trishul.ai.speech.model;

import io.trishul.model.base.mapper.BaseMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AiSpeechConfigMapper extends
    BaseMapper<AiSpeechConfig, AiSpeechConfigDto, AddAiSpeechConfigDto, UpdateAiSpeechConfigDto> {
  AiSpeechConfigMapper INSTANCE = Mappers.getMapper(AiSpeechConfigMapper.class);

  @Mapping(target = AiSpeechConfig.ATTR_LAST_UPDATED, ignore = true)
  @Mapping(target = AiSpeechConfig.ATTR_CREATED_AT, ignore = true)
  @Mapping(target = AiSpeechConfig.ATTR_VERSION, ignore = true)
  @Mapping(target = AiSpeechConfig.ATTR_NAME, ignore = true)
  @Mapping(target = AiSpeechConfig.ATTR_PROVIDER, ignore = true)
  @Mapping(target = AiSpeechConfig.ATTR_TTS_MODEL_NAME, ignore = true)
  @Mapping(target = AiSpeechConfig.ATTR_STT_MODEL_NAME, ignore = true)
  @Mapping(target = AiSpeechConfig.ATTR_VOICE, ignore = true)
  @Mapping(target = AiSpeechConfig.ATTR_SPEED, ignore = true)
  @Mapping(target = AiSpeechConfig.ATTR_IS_DEFAULT, ignore = true)
  AiSpeechConfig fromDto(Long id);

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
