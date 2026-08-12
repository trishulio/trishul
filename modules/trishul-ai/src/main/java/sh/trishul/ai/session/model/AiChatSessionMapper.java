package sh.trishul.ai.session.model;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import sh.trishul.ai.agent.model.AiAgentConfigMapper;
import sh.trishul.ai.memory.model.AiChatMemoryConfigMapper;
import sh.trishul.model.base.mapper.BaseMapper;
import sh.trishul.model.mapper.DeleteResultMapper;

@Mapper(
    uses = {AiAgentConfigMapper.class, AiChatMemoryConfigMapper.class, DeleteResultMapper.class})
public interface AiChatSessionMapper extends
    BaseMapper<AiChatSession, AiChatSessionDto, AddAiChatSessionDto, UpdateAiChatSessionDto> {
  AiChatSessionMapper INSTANCE = Mappers.getMapper(AiChatSessionMapper.class);

  @Mapping(target = AiChatSession.ATTR_LAST_UPDATED, ignore = true)
  @Mapping(target = AiChatSession.ATTR_CREATED_AT, ignore = true)
  @Mapping(target = AiChatSession.ATTR_VERSION, ignore = true)
  @Mapping(target = AiChatSession.ATTR_SESSION_KEY, ignore = true)
  @Mapping(target = AiChatSession.ATTR_TITLE, ignore = true)
  @Mapping(target = AiChatSession.ATTR_IS_ACTIVE, ignore = true)
  @Mapping(target = AiChatSession.ATTR_AGENT_CONFIG, ignore = true)
  @Mapping(target = AiChatSession.ATTR_CHAT_MEMORY_CONFIG, ignore = true)
  AiChatSession fromDto(Long id);

  @Override
  @Mapping(target = AiChatSession.ATTR_ID, ignore = true)
  @Mapping(target = AiChatSession.ATTR_LAST_UPDATED, ignore = true)
  @Mapping(target = AiChatSession.ATTR_CREATED_AT, ignore = true)
  @Mapping(target = AiChatSession.ATTR_VERSION, ignore = true)
  @Mapping(target = AiChatSession.ATTR_AGENT_CONFIG, source = "agentConfigId")
  @Mapping(target = AiChatSession.ATTR_CHAT_MEMORY_CONFIG, source = "chatMemoryConfigId")
  AiChatSession fromAddDto(AddAiChatSessionDto addDto);

  @Override
  @Mapping(target = AiChatSession.ATTR_LAST_UPDATED, ignore = true)
  @Mapping(target = AiChatSession.ATTR_CREATED_AT, ignore = true)
  @Mapping(target = AiChatSession.ATTR_SESSION_KEY, ignore = true)
  @Mapping(target = AiChatSession.ATTR_AGENT_CONFIG, source = "agentConfigId")
  @Mapping(target = AiChatSession.ATTR_CHAT_MEMORY_CONFIG, source = "chatMemoryConfigId")
  AiChatSession fromUpdateDto(UpdateAiChatSessionDto updateDto);

  @Override
  AiChatSessionDto toDto(AiChatSession entity);
}
