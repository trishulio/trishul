package io.trishul.ai.service.speech.controller;

public class TtsRequest {
  private String text;
  private String voice;

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }

  public String getVoice() {
    return voice;
  }

  public void setVoice(String voice) {
    this.voice = voice;
  }
}
