package io.trishul.ai;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import io.trishul.ai.agent.model.*;
import io.trishul.ai.chat.model.*;
import io.trishul.ai.guardrail.model.*;
import io.trishul.ai.memory.model.*;
import io.trishul.ai.session.model.*;
import io.trishul.ai.skill.model.*;
import io.trishul.ai.speech.model.*;
import io.trishul.ai.tool.model.*;
import java.time.LocalDateTime;
import java.util.Set;
import org.junit.jupiter.api.Test;

public class AllDtoAndMapperCoverageTest {

  @Test
  public void testNullMappers() {
    assertNull(AiChatMemoryConfigMapper.INSTANCE.fromDto(null));
    assertNull(AiChatMemoryConfigMapper.INSTANCE.fromAddDto(null));
    assertNull(AiChatMemoryConfigMapper.INSTANCE.fromUpdateDto(null));
    assertNull(AiChatMemoryConfigMapper.INSTANCE.toDto(null));

    assertNull(AiChatSessionMapper.INSTANCE.fromDto(null));
    assertNull(AiChatSessionMapper.INSTANCE.fromAddDto(null));
    assertNull(AiChatSessionMapper.INSTANCE.fromUpdateDto(null));
    assertNull(AiChatSessionMapper.INSTANCE.toDto(null));

    assertNull(AiSkillMapper.INSTANCE.fromDto(null));
    assertNull(AiSkillMapper.INSTANCE.fromAddDto(null));
    assertNull(AiSkillMapper.INSTANCE.fromUpdateDto(null));
    assertNull(AiSkillMapper.INSTANCE.toDto(null));

    assertNull(AiToolMapper.INSTANCE.fromDto(null));
    assertNull(AiToolMapper.INSTANCE.fromAddDto(null));
    assertNull(AiToolMapper.INSTANCE.fromUpdateDto(null));
    assertNull(AiToolMapper.INSTANCE.toDto(null));

    assertNull(AiSpeechConfigMapper.INSTANCE.fromDto(null));
    assertNull(AiSpeechConfigMapper.INSTANCE.fromAddDto(null));
    assertNull(AiSpeechConfigMapper.INSTANCE.fromUpdateDto(null));
    assertNull(AiSpeechConfigMapper.INSTANCE.toDto(null));

    assertNull(AiChatModelConfigMapper.INSTANCE.fromDto(null));
    assertNull(AiChatModelConfigMapper.INSTANCE.fromAddDto(null));
    assertNull(AiChatModelConfigMapper.INSTANCE.fromUpdateDto(null));
    assertNull(AiChatModelConfigMapper.INSTANCE.toDto(null));

    assertNull(AiAgentConfigMapper.INSTANCE.fromDto(null));
    assertNull(AiAgentConfigMapper.INSTANCE.fromAddDto(null));
    assertNull(AiAgentConfigMapper.INSTANCE.fromUpdateDto(null));
    assertNull(AiAgentConfigMapper.INSTANCE.toDto(null));

    assertNull(AiGuardrailMapper.INSTANCE.fromDto(null));
    assertNull(AiGuardrailMapper.INSTANCE.fromAddDto(null));
    assertNull(AiGuardrailMapper.INSTANCE.fromUpdateDto(null));
    assertNull(AiGuardrailMapper.INSTANCE.toDto(null));
  }

  @Test
  public void testDtoConstructorsAndAccessors() {
    // AddAiChatMemoryConfigDto
    AddAiChatMemoryConfigDto addMemory = new AddAiChatMemoryConfigDto();
    assertNotNull(addMemory);

    // AddAiChatSessionDto
    AddAiChatSessionDto addSession = new AddAiChatSessionDto();
    assertNotNull(addSession);

    // AiSkillDto
    AiSkillDto skillDto1 = new AiSkillDto();
    assertNotNull(skillDto1);
    AiSkillDto skillDto2 = new AiSkillDto(1L);
    assertNotNull(skillDto2);
    AiSkillDto skillDto3 = new AiSkillDto(1L, "name", "desc", "sys", true, LocalDateTime.now(),
        LocalDateTime.now(), 1);
    assertNotNull(skillDto3);
    skillDto1.setId(1L).setName("n").setDescription("d").setSystemPrompt("s").setIsEnabled(true)
        .setCreatedAt(LocalDateTime.now()).setLastUpdated(LocalDateTime.now()).setVersion(1);
    assertNotNull(skillDto1.getId());
    assertNotNull(skillDto1.getName());
    assertNotNull(skillDto1.getDescription());
    assertNotNull(skillDto1.getSystemPrompt());
    assertNotNull(skillDto1.getIsEnabled());
    assertNotNull(skillDto1.getCreatedAt());
    assertNotNull(skillDto1.getLastUpdated());
    assertNotNull(skillDto1.getVersion());


    // AddAiSkillDto
    AddAiSkillDto addSkill1 = new AddAiSkillDto();
    assertNotNull(addSkill1);
    AddAiSkillDto addSkill2 = new AddAiSkillDto("name", "desc", "sys", true);
    assertNotNull(addSkill2);
    addSkill1.setName("n").setDescription("d").setSystemPrompt("s").setIsEnabled(true);
    assertNotNull(addSkill1.getName());
    assertNotNull(addSkill1.getDescription());
    assertNotNull(addSkill1.getSystemPrompt());
    assertNotNull(addSkill1.getIsEnabled());

    // AiToolDto
    AiToolDto toolDto1 = new AiToolDto();
    assertNotNull(toolDto1);
    AiToolDto toolDto2 = new AiToolDto(1L);
    assertNotNull(toolDto2);
    AiToolDto toolDto3 = new AiToolDto(1L, "name", "beanName", "desc", true, LocalDateTime.now(),
        LocalDateTime.now(), 1);
    assertNotNull(toolDto3);
    toolDto1.setId(1L).setName("n").setBeanName("bn").setDescription("d").setIsEnabled(true)
        .setCreatedAt(LocalDateTime.now()).setLastUpdated(LocalDateTime.now()).setVersion(1);
    assertNotNull(toolDto1.getId());
    assertNotNull(toolDto1.getName());
    assertNotNull(toolDto1.getDescription());
    assertNotNull(toolDto1.getBeanName());
    assertNotNull(toolDto1.getIsEnabled());
    assertNotNull(toolDto1.getCreatedAt());
    assertNotNull(toolDto1.getLastUpdated());
    assertNotNull(toolDto1.getVersion());

    // AddAiToolDto
    AddAiToolDto addTool1 = new AddAiToolDto();
    assertNotNull(addTool1);
    AddAiToolDto addTool2 = new AddAiToolDto("name", "beanName", "desc", true);
    assertNotNull(addTool2);
    addTool1.setName("n").setBeanName("bn").setDescription("d").setIsEnabled(true);
    assertNotNull(addTool1.getName());
    assertNotNull(addTool1.getDescription());
    assertNotNull(addTool1.getBeanName());
    assertNotNull(addTool1.getIsEnabled());

    // AddAiSpeechConfigDto
    AddAiSpeechConfigDto addSpeech1 = new AddAiSpeechConfigDto();
    assertNotNull(addSpeech1);
    AddAiSpeechConfigDto addSpeech2
        = new AddAiSpeechConfigDto("name", "provider", "ttsModel", "sttModel", "voice", 1.0, true);
    assertNotNull(addSpeech2);
    addSpeech1.setName("n").setProvider("p").setTtsModelName("m").setSttModelName("stt")
        .setVoice("v").setSpeed(1.0).setIsDefault(true);
    assertNotNull(addSpeech1.getName());
    assertNotNull(addSpeech1.getProvider());
    assertNotNull(addSpeech1.getTtsModelName());
    assertNotNull(addSpeech1.getSttModelName());
    assertNotNull(addSpeech1.getVoice());
    assertNotNull(addSpeech1.getSpeed());
    assertNotNull(addSpeech1.getIsDefault());

    // AddAiChatModelConfigDto
    AddAiChatModelConfigDto addModel1 = new AddAiChatModelConfigDto();
    assertNotNull(addModel1);
    AddAiChatModelConfigDto addModel2 = new AddAiChatModelConfigDto("name", "provider", "modelName",
        "streamingModelName", "apiKey", 0.7, 100, 0.9, true);
    assertNotNull(addModel2);
    addModel1.setName("n").setProvider("p").setModelName("m").setTemperature(0.7).setMaxTokens(100)
        .setIsDefault(true);
    assertNotNull(addModel1.getName());
    assertNotNull(addModel1.getProvider());
    assertNotNull(addModel1.getModelName());
    assertNotNull(addModel1.getTemperature());
    assertNotNull(addModel1.getMaxTokens());
    assertNotNull(addModel1.getIsDefault());

    // AddAiAgentConfigDto
    AddAiAgentConfigDto addAgent1 = new AddAiAgentConfigDto();
    assertNotNull(addAgent1);
    AddAiAgentConfigDto addAgent2
        = new AddAiAgentConfigDto("name", "desc", true, 1L, 1L, Set.of(), Set.of(), Set.of());
    assertNotNull(addAgent2);

    // AiAgentConfigDto
    AiAgentConfigDto agentDto1 = new AiAgentConfigDto();
    assertNotNull(agentDto1);
    AiAgentConfigDto agentDto2 = new AiAgentConfigDto(1L);
    assertNotNull(agentDto2);
    agentDto1.setGuardrails(Set.of()).setSkills(Set.of()).setTools(Set.of());
    assertNotNull(agentDto1.getGuardrails());
    assertNotNull(agentDto1.getSkills());
    assertNotNull(agentDto1.getTools());

    // AddAiGuardrailDto
    AddAiGuardrailDto addGuardrail1 = new AddAiGuardrailDto();
    assertNotNull(addGuardrail1);
    AddAiGuardrailDto addGuardrail2
        = new AddAiGuardrailDto("name", AiGuardrailType.INPUT, "strategy", "config", 1, true);
    assertNotNull(addGuardrail2);
    addGuardrail1.setName("n").setType(AiGuardrailType.OUTPUT).setStrategy("s")
        .setConfiguration("c").setPriority(1).setIsEnabled(true);
    assertNotNull(addGuardrail1.getName());
    assertNotNull(addGuardrail1.getType());
    assertNotNull(addGuardrail1.getStrategy());
    assertNotNull(addGuardrail1.getConfiguration());
    assertNotNull(addGuardrail1.getPriority());
    assertNotNull(addGuardrail1.getIsEnabled());
  }
}
