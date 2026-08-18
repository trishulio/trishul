package io.trishul.ai.service.chat.execution.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.List;
import org.junit.jupiter.api.Test;

class ChatRequestDtoTest {

  @Test
  void testGettersAndSetters() {
    ChatRequestDto dto = new ChatRequestDto();

    assertNull(dto.getSessionId());
    dto.setSessionId("session-1");
    assertEquals("session-1", dto.getSessionId());

    assertNull(dto.getMessage());
    dto.setMessage("hello");
    assertEquals("hello", dto.getMessage());

    assertNull(dto.getContents());
    List<ChatMessageContentDto> contents = List.of(new ChatMessageContentDto());
    dto.setContents(contents);
    assertEquals(contents, dto.getContents());
  }
}
