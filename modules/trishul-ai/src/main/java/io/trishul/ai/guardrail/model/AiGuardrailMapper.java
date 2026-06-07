package io.trishul.ai.guardrail.model;

import io.trishul.model.base.mapper.BaseMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AiGuardrailMapper
    extends BaseMapper<AiGuardrail, AiGuardrailDto, AddAiGuardrailDto, UpdateAiGuardrailDto> {
  AiGuardrailMapper INSTANCE = Mappers.getMapper(AiGuardrailMapper.class);

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
