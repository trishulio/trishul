package io.trishul.integration.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.mock;

import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;

class IntegrationDtoTest {
  @Test
  void testAccessId() throws Exception {
    IntegrationDto accessor = new IntegrationDto();
    assertSame(accessor, accessor.setId(123L));
    assertEquals(123L, accessor.getId());
  }

  @Test
  void testAccessName() throws Exception {
    IntegrationDto accessor = new IntegrationDto();
    assertSame(accessor, accessor.setName("testString"));
    assertEquals("testString", accessor.getName());
  }

  @Test
  void testAccessType() throws Exception {
    IntegrationDto accessor = new IntegrationDto();
    IntegrationType value = mock(IntegrationType.class);
    assertSame(accessor, accessor.setType(value));
    assertEquals(value, accessor.getType());
  }

  @Test
  void testAccessProvider() throws Exception {
    IntegrationDto accessor = new IntegrationDto();
    assertSame(accessor, accessor.setProvider("testString"));
    assertEquals("testString", accessor.getProvider());
  }

  @Test
  void testAccessStatus() throws Exception {
    IntegrationDto accessor = new IntegrationDto();
    IntegrationStatus value = mock(IntegrationStatus.class);
    assertSame(accessor, accessor.setStatus(value));
    assertEquals(value, accessor.getStatus());
  }

  @Test
  void testAccessConfiguration() throws Exception {
    IntegrationDto accessor = new IntegrationDto();
    assertSame(accessor, accessor.setConfiguration("testString"));
    assertEquals("testString", accessor.getConfiguration());
  }

  @Test
  void testAccessCreatedAt() throws Exception {
    IntegrationDto accessor = new IntegrationDto();
    LocalDateTime value = LocalDateTime.of(2000, 1, 1, 0, 0);
    assertSame(accessor, accessor.setCreatedAt(value));
    assertEquals(value, accessor.getCreatedAt());
  }

  @Test
  void testAccessLastUpdated() throws Exception {
    IntegrationDto accessor = new IntegrationDto();
    LocalDateTime value = LocalDateTime.of(2000, 1, 1, 0, 0);
    assertSame(accessor, accessor.setLastUpdated(value));
    assertEquals(value, accessor.getLastUpdated());
  }

  @Test
  void testAccessVersion() throws Exception {
    IntegrationDto accessor = new IntegrationDto();
    assertSame(accessor, accessor.setVersion(123));
    assertEquals(123, accessor.getVersion());
  }
}
