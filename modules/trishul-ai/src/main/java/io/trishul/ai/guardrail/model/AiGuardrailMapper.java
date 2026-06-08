package io.trishul.ai.guardrail.model;

import io.trishul.model.base.mapper.BaseMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AiGuardrailMapper
    extends BaseMapper<AiGuardrail, AiGuardrailDto, AddAiGuardrailDto, UpdateAiGuardrailDto> {
  AiGuardrailMapper INSTANCE = Mappers.getMapper(AiGuardrailMapper.class);

  @Mapping(target = AiGuardrail.ATTR_LAST_UPDATED, ignore = true)
  @Mapping(target = AiGuardrail.ATTR_CREATED_AT, ignore = true)
  @Mapping(target = AiGuardrail.ATTR_VERSION, ignore = true)
  @Mapping(target = AiGuardrail.ATTR_NAME, ignore = true)
  @Mapping(target = AiGuardrail.ATTR_TYPE, ignore = true)
  @Mapping(target = AiGuardrail.ATTR_STRATEGY, ignore = true)
  @Mapping(target = AiGuardrail.ATTR_CONFIGURATION, ignore = true)
  @Mapping(target = AiGuardrail.ATTR_PRIORITY, ignore = true)
  @Mapping(target = AiGuardrail.ATTR_IS_ENABLED, ignore = true)
  AiGuardrail fromDto(Long id);

  @Override
  @Mapping(target = AiGuardrail.ATTR_ID, ignore = true)
  @Mapping(target = AiGuardrail.ATTR_LAST_UPDATED, ignore = true)
  @Mapping(target = AiGuardrail.ATTR_CREATED_AT, ignore = true)
  @Mapping(target = AiGuardrail.ATTR_VERSION, ignore = true)
  AiGuardrail fromAddDto(AddAiGuardrailDto addDto);

  @Override
  @Mapping(target = AiGuardrail.ATTR_LAST_UPDATED, ignore = true)
  @Mapping(target = AiGuardrail.ATTR_CREATED_AT, ignore = true)
  AiGuardrail fromUpdateDto(UpdateAiGuardrailDto updateDto);

  @Override
  AiGuardrailDto toDto(AiGuardrail entity);
}
