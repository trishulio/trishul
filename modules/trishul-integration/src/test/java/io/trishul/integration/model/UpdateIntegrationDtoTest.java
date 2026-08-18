package io.trishul.integration.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.Test;

class UpdateIntegrationDtoTest {
  @Test
  void testAccessId() throws Exception {
    UpdateIntegrationDto accessor = new UpdateIntegrationDto();
    assertSame(accessor, accessor.setId(123L));
    assertEquals(123L, accessor.getId());
  }

  @Test
  void testAccessName() throws Exception {
    UpdateIntegrationDto accessor = new UpdateIntegrationDto();
    assertSame(accessor, accessor.setName("testString"));
    assertEquals("testString", accessor.getName());
  }

  @Test
  void testAccessType() throws Exception {
    UpdateIntegrationDto accessor = new UpdateIntegrationDto();
    IntegrationType value = mock(IntegrationType.class);
    assertSame(accessor, accessor.setType(value));
    assertEquals(value, accessor.getType());
  }

  @Test
  void testAccessProvider() throws Exception {
    UpdateIntegrationDto accessor = new UpdateIntegrationDto();
    assertSame(accessor, accessor.setProvider("testString"));
    assertEquals("testString", accessor.getProvider());
  }

  @Test
  void testAccessStatus() throws Exception {
    UpdateIntegrationDto accessor = new UpdateIntegrationDto();
    IntegrationStatus value = mock(IntegrationStatus.class);
    assertSame(accessor, accessor.setStatus(value));
    assertEquals(value, accessor.getStatus());
  }

  @Test
  void testAccessConfiguration() throws Exception {
    UpdateIntegrationDto accessor = new UpdateIntegrationDto();
    assertSame(accessor, accessor.setConfiguration("testString"));
    assertEquals("testString", accessor.getConfiguration());
  }

  @Test
  void testAccessVersion() throws Exception {
    UpdateIntegrationDto accessor = new UpdateIntegrationDto();
    assertSame(accessor, accessor.setVersion(123));
    assertEquals(123, accessor.getVersion());
  }
}
