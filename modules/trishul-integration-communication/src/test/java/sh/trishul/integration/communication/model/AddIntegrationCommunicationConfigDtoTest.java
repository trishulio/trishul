package sh.trishul.integration.communication.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.Test;
import sh.trishul.communication.model.channel.ChannelType;

class AddIntegrationCommunicationConfigDtoTest {
  @Test
  void testAccessIntegrationId() throws Exception {
    AddIntegrationCommunicationConfigDto accessor = new AddIntegrationCommunicationConfigDto();
    assertSame(accessor, accessor.setIntegrationId(123L));
    assertEquals(123L, accessor.getIntegrationId());
  }

  @Test
  void testAccessChannelType() throws Exception {
    AddIntegrationCommunicationConfigDto accessor = new AddIntegrationCommunicationConfigDto();
    ChannelType value = mock(ChannelType.class);
    assertSame(accessor, accessor.setChannelType(value));
    assertEquals(value, accessor.getChannelType());
  }

  @Test
  void testAccessChannelAddress() throws Exception {
    AddIntegrationCommunicationConfigDto accessor = new AddIntegrationCommunicationConfigDto();
    assertSame(accessor, accessor.setChannelAddress("testString"));
    assertEquals("testString", accessor.getChannelAddress());
  }

  @Test
  void testAccessDefaultFrom() throws Exception {
    AddIntegrationCommunicationConfigDto accessor = new AddIntegrationCommunicationConfigDto();
    assertSame(accessor, accessor.setDefaultFrom("testString"));
    assertEquals("testString", accessor.getDefaultFrom());
  }

  @Test
  void testAccessEnabled() throws Exception {
    AddIntegrationCommunicationConfigDto accessor = new AddIntegrationCommunicationConfigDto();
    assertSame(accessor, accessor.setEnabled(true));
    assertEquals(true, accessor.getEnabled());
    assertSame(accessor, accessor.setEnabled(false));
    assertEquals(false, accessor.getEnabled());
    assertSame(accessor, accessor.setEnabled(null));
    assertEquals(null, accessor.getEnabled());
  }
}
