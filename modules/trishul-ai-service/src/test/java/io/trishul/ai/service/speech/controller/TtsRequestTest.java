package io.trishul.ai.service.speech.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

class TtsRequestTest {

  @Test
  void testGettersAndSetters() {
    TtsRequest request = new TtsRequest();
    assertNull(request.getText());
    assertNull(request.getVoice());

    request.setText("Hello");
    request.setVoice("alloy");

    assertEquals("Hello", request.getText());
    assertEquals("alloy", request.getVoice());
  }
}
