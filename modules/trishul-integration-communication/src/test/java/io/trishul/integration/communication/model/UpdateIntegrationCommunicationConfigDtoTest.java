package io.trishul.integration.communication.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.mock;

import io.trishul.communication.model.channel.ChannelType;
import org.junit.jupiter.api.Test;

class UpdateIntegrationCommunicationConfigDtoTest {
  @Test
  void testAccessId() throws Exception {
    UpdateIntegrationCommunicationConfigDto accessor
        = new UpdateIntegrationCommunicationConfigDto();
    assertSame(accessor, accessor.setId(123L));
    assertEquals(123L, accessor.getId());
  }

  @Test
  void testAccessIntegrationId() throws Exception {
    UpdateIntegrationCommunicationConfigDto accessor
        = new UpdateIntegrationCommunicationConfigDto();
    assertSame(accessor, accessor.setIntegrationId(123L));
    assertEquals(123L, accessor.getIntegrationId());
  }

  @Test
  void testAccessChannelType() throws Exception {
    UpdateIntegrationCommunicationConfigDto accessor
        = new UpdateIntegrationCommunicationConfigDto();
    ChannelType value = mock(ChannelType.class);
    assertSame(accessor, accessor.setChannelType(value));
    assertEquals(value, accessor.getChannelType());
  }

  @Test
  void testAccessChannelAddress() throws Exception {
    UpdateIntegrationCommunicationConfigDto accessor
        = new UpdateIntegrationCommunicationConfigDto();
    assertSame(accessor, accessor.setChannelAddress("testString"));
    assertEquals("testString", accessor.getChannelAddress());
  }

  @Test
  void testAccessDefaultFrom() throws Exception {
    UpdateIntegrationCommunicationConfigDto accessor
        = new UpdateIntegrationCommunicationConfigDto();
    assertSame(accessor, accessor.setDefaultFrom("testString"));
    assertEquals("testString", accessor.getDefaultFrom());
  }

  @Test
  void testAccessEnabled() throws Exception {
    UpdateIntegrationCommunicationConfigDto accessor
        = new UpdateIntegrationCommunicationConfigDto();
    assertSame(accessor, accessor.setEnabled(true));
    assertEquals(true, accessor.getEnabled());
  }

  @Test
  void testAccessVersion() throws Exception {
    UpdateIntegrationCommunicationConfigDto accessor
        = new UpdateIntegrationCommunicationConfigDto();
    assertSame(accessor, accessor.setVersion(123));
    assertEquals(123, accessor.getVersion());
  }
}
