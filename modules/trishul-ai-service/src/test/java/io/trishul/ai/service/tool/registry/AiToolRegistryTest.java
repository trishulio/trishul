package io.trishul.ai.service.tool.registry;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import org.junit.jupiter.api.Test;

class AiToolRegistryTest {

  @Test
  void testGetToolsByIds_ReturnsEmptyList() {
    AiToolRegistry registry = new AiToolRegistry();
    List<Object> tools = registry.getToolsByIds(List.of(1L, 2L));
    assertTrue(tools.isEmpty());
  }
}
