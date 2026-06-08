package io.trishul.ai.session.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import io.trishul.ai.agent.model.AiAgentConfig;
import io.trishul.ai.agent.model.AiAgentConfigDto;
import io.trishul.ai.memory.model.AiChatMemoryConfig;
import io.trishul.ai.memory.model.AiChatMemoryConfigDto;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AiChatSessionMapperTest {
  private AiChatSessionMapper mapper;

  @BeforeEach
  void init() {
    mapper = AiChatSessionMapper.INSTANCE;
  }

  @Test
  void testFromDto_ReturnsNull_WhenIdIsNull() {
    assertNull(mapper.fromDto(null));
  }

  @Test
  void testFromDto_ReturnsPojo_WhenIdIsNotNull() {
    AiChatSession expected = new AiChatSession(1L);
    assertEquals(expected, mapper.fromDto(1L));
  }

  @Test
  void testFromAddDto_ReturnsEntity_WhenAddDtoIsNotNull() {
    AddAiChatSessionDto dto = new AddAiChatSessionDto("key", "title", true, 1L, 1L);

    AiChatSession entity = mapper.fromAddDto(dto);

    AiChatSession expected
        = new AiChatSession().setSessionKey("key").setTitle("title").setIsActive(true)
            .setAgentConfig(new AiAgentConfig(1L)).setChatMemoryConfig(new AiChatMemoryConfig(1L));

    assertEquals(expected, entity);
  }

  @Test
  void testFromUpdateDto_ReturnsEntity_WhenUpdateDtoIsNotNull() {
    UpdateAiChatSessionDto dto = new UpdateAiChatSessionDto(1L, "title", true, 2L, 2L, 1);

    AiChatSession entity = mapper.fromUpdateDto(dto);

    AiChatSession expected = new AiChatSession().setId(1L).setTitle("title").setIsActive(true)
        .setAgentConfig(new AiAgentConfig(2L)).setChatMemoryConfig(new AiChatMemoryConfig(2L))
        .setVersion(1);

    assertEquals(expected, entity);
  }

  @Test
  void testToDto_ReturnsDto_WhenEntityIsNotNull() {
    LocalDateTime now = LocalDateTime.now();
    AiChatSession entity = new AiChatSession(1L, "key", "title", true, new AiAgentConfig(1L),
        new AiChatMemoryConfig(1L), now, now, 1);

    AiChatSessionDto dto = mapper.toDto(entity);

    AiChatSessionDto expected = new AiChatSessionDto(1L, "key", "title", true,
        new AiAgentConfigDto(1L), new AiChatMemoryConfigDto(1L), now, now, 1);

    assertEquals(expected, dto);
  }
}
