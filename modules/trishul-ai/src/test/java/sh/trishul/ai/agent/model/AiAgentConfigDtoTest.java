package sh.trishul.ai.agent.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

import java.time.LocalDateTime;
import java.util.Set;
import org.junit.jupiter.api.Test;
import sh.trishul.ai.chat.model.AiChatModelConfigDto;
import sh.trishul.ai.guardrail.model.AiGuardrailDto;
import sh.trishul.ai.memory.model.AiChatMemoryConfigDto;
import sh.trishul.ai.skill.model.AiSkillDto;
import sh.trishul.ai.tool.model.AiToolDto;

class AiAgentConfigDtoTest {

  @Test
  void testGettersAndSetters() {
    LocalDateTime now = LocalDateTime.now();
    AiChatModelConfigDto chatModel = new AiChatModelConfigDto(1L);
    AiChatMemoryConfigDto memoryConfig = new AiChatMemoryConfigDto(1L);

    AiAgentConfigDto dto = new AiAgentConfigDto(1L, "name", "desc", true, chatModel, memoryConfig,
        Set.of(), Set.of(), Set.of(), now, now, 1);

    assertEquals(1L, dto.getId());
    assertEquals("name", dto.getName());
    assertEquals("desc", dto.getDescription());
    assertTrue(dto.getIsActive());
    assertEquals(chatModel, dto.getChatModelConfig());
    assertEquals(memoryConfig, dto.getChatMemoryConfig());
    assertEquals(now, dto.getCreatedAt());
    assertEquals(now, dto.getLastUpdated());
    assertEquals(1, dto.getVersion());

    dto.setId(2L).setName("new-name").setDescription("new-desc").setIsActive(false)
        .setChatModelConfig(new AiChatModelConfigDto(2L))
        .setChatMemoryConfig(new AiChatMemoryConfigDto(2L)).setCreatedAt(now.plusDays(1))
        .setLastUpdated(now.plusDays(1)).setVersion(2);

    assertEquals(2L, dto.getId());
    assertEquals("new-name", dto.getName());
    assertEquals("new-desc", dto.getDescription());
    assertTrue(!dto.getIsActive());
    assertEquals(2L, dto.getChatModelConfig().getId());
    assertEquals(2L, dto.getChatMemoryConfig().getId());
    assertEquals(now.plusDays(1), dto.getCreatedAt());
    assertEquals(now.plusDays(1), dto.getLastUpdated());
    assertEquals(2, dto.getVersion());
  }

  @Test
  void testAccessId() throws Exception {
    AiAgentConfigDto accessor = new AiAgentConfigDto();
    assertSame(accessor, accessor.setId(123L));
    assertEquals(123L, accessor.getId());
  }

  @Test
  void testAccessName() throws Exception {
    AiAgentConfigDto accessor = new AiAgentConfigDto();
    assertSame(accessor, accessor.setName("testString"));
    assertEquals("testString", accessor.getName());
  }

  @Test
  void testAccessDescription() throws Exception {
    AiAgentConfigDto accessor = new AiAgentConfigDto();
    assertSame(accessor, accessor.setDescription("testString"));
    assertEquals("testString", accessor.getDescription());
  }

  @Test
  void testAccessIsActive() throws Exception {
    AiAgentConfigDto accessor = new AiAgentConfigDto();
    assertSame(accessor, accessor.setIsActive(true));
    assertEquals(true, accessor.getIsActive());
  }

  @Test
  void testAccessChatModelConfig() throws Exception {
    AiAgentConfigDto accessor = new AiAgentConfigDto();
    AiChatModelConfigDto value = mock(AiChatModelConfigDto.class);
    assertSame(accessor, accessor.setChatModelConfig(value));
    assertEquals(value, accessor.getChatModelConfig());
  }

  @Test
  void testAccessChatMemoryConfig() throws Exception {
    AiAgentConfigDto accessor = new AiAgentConfigDto();
    AiChatMemoryConfigDto value = mock(AiChatMemoryConfigDto.class);
    assertSame(accessor, accessor.setChatMemoryConfig(value));
    assertEquals(value, accessor.getChatMemoryConfig());
  }

  @Test
  void testAccessGuardrails() throws Exception {
    AiAgentConfigDto accessor = new AiAgentConfigDto();
    Set<AiGuardrailDto> value = Set.of(mock(AiGuardrailDto.class));
    assertSame(accessor, accessor.setGuardrails(value));
    assertEquals(value, accessor.getGuardrails());
  }

  @Test
  void testAccessSkills() throws Exception {
    AiAgentConfigDto accessor = new AiAgentConfigDto();
    Set<AiSkillDto> value = Set.of(mock(AiSkillDto.class));
    assertSame(accessor, accessor.setSkills(value));
    assertEquals(value, accessor.getSkills());
  }

  @Test
  void testAccessTools() throws Exception {
    AiAgentConfigDto accessor = new AiAgentConfigDto();
    Set<AiToolDto> value = Set.of(mock(AiToolDto.class));
    assertSame(accessor, accessor.setTools(value));
    assertEquals(value, accessor.getTools());
  }

  @Test
  void testAccessCreatedAt() throws Exception {
    AiAgentConfigDto accessor = new AiAgentConfigDto();
    LocalDateTime value = LocalDateTime.of(2000, 1, 1, 0, 0);
    assertSame(accessor, accessor.setCreatedAt(value));
    assertEquals(value, accessor.getCreatedAt());
  }

  @Test
  void testAccessLastUpdated() throws Exception {
    AiAgentConfigDto accessor = new AiAgentConfigDto();
    LocalDateTime value = LocalDateTime.of(2000, 1, 1, 0, 0);
    assertSame(accessor, accessor.setLastUpdated(value));
    assertEquals(value, accessor.getLastUpdated());
  }

  @Test
  void testAccessVersion() throws Exception {
    AiAgentConfigDto accessor = new AiAgentConfigDto();
    assertSame(accessor, accessor.setVersion(123));
    assertEquals(123, accessor.getVersion());
  }

}
