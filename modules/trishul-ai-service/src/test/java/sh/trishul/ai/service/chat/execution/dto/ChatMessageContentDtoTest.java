package sh.trishul.ai.service.chat.execution.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

class ChatMessageContentDtoTest {

  @Test
  void testGettersAndSetters() {
    ChatMessageContentDto dto = new ChatMessageContentDto();

    dto.setType(ChatMessageContentDto.Type.TEXT);
    assertEquals(ChatMessageContentDto.Type.TEXT, dto.getType());

    dto.setText("hello");
    assertEquals("hello", dto.getText());

    dto.setUrl("http://example.com");
    assertEquals("http://example.com", dto.getUrl());

    dto.setBase64Data("base64");
    assertEquals("base64", dto.getBase64Data());

    dto.setMimeType("image/png");
    assertEquals("image/png", dto.getMimeType());
  }

  @Test
  void testEnumValues() {
    assertNotNull(ChatMessageContentDto.Type.valueOf("TEXT"));
    assertNotNull(ChatMessageContentDto.Type.valueOf("IMAGE"));
    assertEquals(2, ChatMessageContentDto.Type.values().length);
  }
}
