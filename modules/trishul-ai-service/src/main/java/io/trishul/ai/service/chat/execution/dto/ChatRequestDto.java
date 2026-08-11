package io.trishul.ai.service.chat.execution.dto;

import java.util.List;

public class ChatRequestDto {
  private String sessionId;
  private String message;
  private List<ChatMessageContentDto> contents;

  public String getSessionId() {
    return sessionId;
  }

  public void setSessionId(String sessionId) {
    this.sessionId = sessionId;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public List<ChatMessageContentDto> getContents() {
    return contents;
  }

  public void setContents(List<ChatMessageContentDto> contents) {
    this.contents = contents;
  }
}
