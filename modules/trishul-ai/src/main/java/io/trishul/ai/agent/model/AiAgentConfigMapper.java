package io.trishul.ai.agent.model;

import io.trishul.ai.chat.model.AiChatModelConfigMapper;
import io.trishul.ai.guardrail.model.AiGuardrailMapper;
import io.trishul.ai.memory.model.AiChatMemoryConfigMapper;
import io.trishul.ai.skill.model.AiSkillMapper;
import io.trishul.ai.tool.model.AiToolMapper;
import io.trishul.model.base.mapper.BaseMapper;
import io.trishul.model.mapper.DeleteResultMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(uses = {AiChatModelConfigMapper.class, AiChatMemoryConfigMapper.class,
    AiGuardrailMapper.class, AiSkillMapper.class, AiToolMapper.class, DeleteResultMapper.class})
public interface AiAgentConfigMapper extends
    BaseMapper<AiAgentConfig, AiAgentConfigDto, AddAiAgentConfigDto, UpdateAiAgentConfigDto> {
  AiAgentConfigMapper INSTANCE = Mappers.getMapper(AiAgentConfigMapper.class);

  @Mapping(target = AiAgentConfig.ATTR_LAST_UPDATED, ignore = true)
  @Mapping(target = AiAgentConfig.ATTR_CREATED_AT, ignore = true)
  @Mapping(target = AiAgentConfig.ATTR_VERSION, ignore = true)
  @Mapping(target = AiAgentConfig.ATTR_NAME, ignore = true)
  @Mapping(target = AiAgentConfig.ATTR_DESCRIPTION, ignore = true)
  @Mapping(target = AiAgentConfig.ATTR_IS_ACTIVE, ignore = true)
  @Mapping(target = AiAgentConfig.ATTR_CHAT_MODEL_CONFIG, ignore = true)
  @Mapping(target = AiAgentConfig.ATTR_CHAT_MEMORY_CONFIG, ignore = true)
  AiAgentConfig fromDto(Long id);

  @Override
  @Mapping(target = AiAgentConfig.ATTR_ID, ignore = true)
  @Mapping(target = AiAgentConfig.ATTR_LAST_UPDATED, ignore = true)
  @Mapping(target = AiAgentConfig.ATTR_CREATED_AT, ignore = true)
  @Mapping(target = AiAgentConfig.ATTR_VERSION, ignore = true)
  @Mapping(target = AiAgentConfig.ATTR_CHAT_MODEL_CONFIG, source = "chatModelConfigId")
  @Mapping(target = AiAgentConfig.ATTR_CHAT_MEMORY_CONFIG, source = "chatMemoryConfigId")
  // Note: the sets of guardrails/skills/tools are handled differently in Trishul
  // via
  // EntityMergerService typically.
  // The mapping for many-to-many from flat DTO ids requires custom logic or
  // ignoring them here and
  // doing it in the service.
  // We'll ignore the collections on the entity mapping side from DTOs. The
  // service layer manages
  // the join entities.
  AiAgentConfig fromAddDto(AddAiAgentConfigDto addDto);

  @Override
  @Mapping(target = AiAgentConfig.ATTR_LAST_UPDATED, ignore = true)
  @Mapping(target = AiAgentConfig.ATTR_CREATED_AT, ignore = true)
  @Mapping(target = AiAgentConfig.ATTR_CHAT_MODEL_CONFIG, source = "chatModelConfigId")
  @Mapping(target = AiAgentConfig.ATTR_CHAT_MEMORY_CONFIG, source = "chatMemoryConfigId")
  AiAgentConfig fromUpdateDto(UpdateAiAgentConfigDto updateDto);

  @Override
  @Mapping(target = AiAgentConfigDto.ATTR_GUARDRAILS, ignore = true)
  @Mapping(target = AiAgentConfigDto.ATTR_SKILLS, ignore = true)
  @Mapping(target = AiAgentConfigDto.ATTR_TOOLS, ignore = true)
  AiAgentConfigDto toDto(AiAgentConfig entity);
}
