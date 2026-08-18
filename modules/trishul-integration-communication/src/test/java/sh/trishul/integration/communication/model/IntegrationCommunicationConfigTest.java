package sh.trishul.integration.communication.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.mock;

import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import sh.trishul.communication.model.channel.ChannelType;
import sh.trishul.integration.model.Integration;

class IntegrationCommunicationConfigTest {
  @Test
  void testAccessId() throws Exception {
    IntegrationCommunicationConfig accessor = new IntegrationCommunicationConfig();
    assertSame(accessor, accessor.setId(123L));
    assertEquals(123L, accessor.getId());
  }

  @Test
  void testAccessIntegration() throws Exception {
    IntegrationCommunicationConfig accessor = new IntegrationCommunicationConfig();
    Integration value = new Integration();
    assertSame(accessor, accessor.setIntegration(value));
    assertEquals(value, accessor.getIntegration());
  }

  @Test
  void testAccessChannelType() throws Exception {
    IntegrationCommunicationConfig accessor = new IntegrationCommunicationConfig();
    ChannelType value = mock(ChannelType.class);
    assertSame(accessor, accessor.setChannelType(value));
    assertEquals(value, accessor.getChannelType());
  }

  @Test
  void testAccessChannelAddress() throws Exception {
    IntegrationCommunicationConfig accessor = new IntegrationCommunicationConfig();
    assertSame(accessor, accessor.setChannelAddress("testString"));
    assertEquals("testString", accessor.getChannelAddress());
  }

  @Test
  void testAccessDefaultFrom() throws Exception {
    IntegrationCommunicationConfig accessor = new IntegrationCommunicationConfig();
    assertSame(accessor, accessor.setDefaultFrom("testString"));
    assertEquals("testString", accessor.getDefaultFrom());
  }

  @Test
  void testAccessEnabled() throws Exception {
    IntegrationCommunicationConfig accessor = new IntegrationCommunicationConfig();
    assertSame(accessor, accessor.setEnabled(true));
    assertEquals(true, accessor.getEnabled());
  }

  @Test
  void testAccessVersion() throws Exception {
    IntegrationCommunicationConfig accessor = new IntegrationCommunicationConfig();
    assertSame(accessor, accessor.setVersion(123));
    assertEquals(123, accessor.getVersion());
  }

  @Test
  void testAccessCreatedAt() throws Exception {
    IntegrationCommunicationConfig accessor = new IntegrationCommunicationConfig();
    LocalDateTime value = LocalDateTime.of(2000, 1, 1, 0, 0);
    assertSame(accessor, accessor.setCreatedAt(value));
    assertEquals(value, accessor.getCreatedAt());
  }

  @Test
  void testAccessLastUpdated() throws Exception {
    IntegrationCommunicationConfig accessor = new IntegrationCommunicationConfig();
    LocalDateTime value = LocalDateTime.of(2000, 1, 1, 0, 0);
    assertSame(accessor, accessor.setLastUpdated(value));
    assertEquals(value, accessor.getLastUpdated());
  }
}
