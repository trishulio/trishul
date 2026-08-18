package sh.trishul.ai.skill.model;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import sh.trishul.model.base.mapper.BaseMapper;

@Mapper
public interface AiSkillMapper
    extends BaseMapper<AiSkill, AiSkillDto, AddAiSkillDto, UpdateAiSkillDto> {
  AiSkillMapper INSTANCE = Mappers.getMapper(AiSkillMapper.class);

  @Mapping(target = AiSkill.ATTR_LAST_UPDATED, ignore = true)
  @Mapping(target = AiSkill.ATTR_CREATED_AT, ignore = true)
  @Mapping(target = AiSkill.ATTR_VERSION, ignore = true)
  @Mapping(target = AiSkill.ATTR_NAME, ignore = true)
  @Mapping(target = AiSkill.ATTR_DESCRIPTION, ignore = true)
  @Mapping(target = AiSkill.ATTR_SYSTEM_PROMPT, ignore = true)
  @Mapping(target = AiSkill.ATTR_IS_ENABLED, ignore = true)
  AiSkill fromDto(Long id);

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
