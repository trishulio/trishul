package io.trishul.ai.skill.model;

import io.trishul.model.base.mapper.BaseMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AiSkillMapper
    extends BaseMapper<AiSkill, AiSkillDto, AddAiSkillDto, UpdateAiSkillDto> {
  AiSkillMapper INSTANCE = Mappers.getMapper(AiSkillMapper.class);

  @Override
  @Mapping(target = AiSkill.ATTR_ID, ignore = true)
  @Mapping(target = AiSkill.ATTR_LAST_UPDATED, ignore = true)
  @Mapping(target = AiSkill.ATTR_CREATED_AT, ignore = true)
  @Mapping(target = AiSkill.ATTR_VERSION, ignore = true)
  AiSkill fromAddDto(AddAiSkillDto addDto);

  @Override
  @Mapping(target = AiSkill.ATTR_LAST_UPDATED, ignore = true)
  @Mapping(target = AiSkill.ATTR_CREATED_AT, ignore = true)
  AiSkill fromUpdateDto(UpdateAiSkillDto updateDto);

  @Override
  AiSkillDto toDto(AiSkill entity);
}
