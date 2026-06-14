package io.trishul.ai.agent.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import io.trishul.ai.chat.model.AiChatModelConfig;
import io.trishul.ai.chat.model.AiChatModelConfigDto;
import io.trishul.ai.memory.model.AiChatMemoryConfig;
import io.trishul.ai.memory.model.AiChatMemoryConfigDto;
import java.time.LocalDateTime;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AiAgentConfigMapperTest {
  private AiAgentConfigMapper mapper;

  @BeforeEach
  void init() {
    mapper = AiAgentConfigMapper.INSTANCE;
  }

  @Test
  void testFromDto_ReturnsNull_WhenIdIsNull() {
    assertNull(mapper.fromDto(null));
  }

  @Test
  void testFromDto_ReturnsPojo_WhenIdIsNotNull() {
    AiAgentConfig expected = new AiAgentConfig(1L);
    assertEquals(expected, mapper.fromDto(1L));
  }

  @Test
  void testFromAddDto_ReturnsEntity_WhenAddDtoIsNotNull() {
    AddAiAgentConfigDto dto
        = new AddAiAgentConfigDto("name", "desc", true, 1L, 1L, Set.of(), Set.of(), Set.of());

    AiAgentConfig entity = mapper.fromAddDto(dto);

    AiAgentConfig expected = new AiAgentConfig().setName("name").setDescription("desc")
        .setIsActive(true).setChatModelConfig(new AiChatModelConfig(1L))
        .setChatMemoryConfig(new AiChatMemoryConfig(1L));

    assertEquals(expected, entity);
  }

  @Test
  void testFromUpdateDto_ReturnsEntity_WhenUpdateDtoIsNotNull() {
    UpdateAiAgentConfigDto dto = new UpdateAiAgentConfigDto(1L, "name", "desc", true, 2L, 2L,
        Set.of(), Set.of(), Set.of(), 1);

    AiAgentConfig entity = mapper.fromUpdateDto(dto);

    AiAgentConfig expected = new AiAgentConfig().setId(1L).setName("name").setDescription("desc")
        .setIsActive(true).setChatModelConfig(new AiChatModelConfig(2L))
        .setChatMemoryConfig(new AiChatMemoryConfig(2L)).setVersion(1);

    assertEquals(expected, entity);
  }

  @Test
  void testToDto_ReturnsDto_WhenEntityIsNotNull() {
    LocalDateTime now = LocalDateTime.now();
    AiAgentConfig entity = new AiAgentConfig(1L, "name", "desc", true, new AiChatModelConfig(1L),
        new AiChatMemoryConfig(1L), now, now, 1);

    AiAgentConfigDto dto = mapper.toDto(entity);

    AiAgentConfigDto expected = new AiAgentConfigDto(1L, "name", "desc", true,
        new AiChatModelConfigDto(1L), new AiChatMemoryConfigDto(1L), null, null, null, now, now, 1);

    assertEquals(expected, dto);
  }
}
