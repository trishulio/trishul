package sh.trishul.integration.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.Test;

class AddIntegrationDtoTest {
  @Test
  void testAccessName() throws Exception {
    AddIntegrationDto accessor = new AddIntegrationDto();
    assertSame(accessor, accessor.setName("testString"));
    assertEquals("testString", accessor.getName());
  }

  @Test
  void testAccessType() throws Exception {
    AddIntegrationDto accessor = new AddIntegrationDto();
    IntegrationType value = mock(IntegrationType.class);
    assertSame(accessor, accessor.setType(value));
    assertEquals(value, accessor.getType());
  }

  @Test
  void testAccessProvider() throws Exception {
    AddIntegrationDto accessor = new AddIntegrationDto();
    assertSame(accessor, accessor.setProvider("testString"));
    assertEquals("testString", accessor.getProvider());
  }

  @Test
  void testAccessStatus() throws Exception {
    AddIntegrationDto accessor = new AddIntegrationDto();
    IntegrationStatus value = mock(IntegrationStatus.class);
    assertSame(accessor, accessor.setStatus(value));
    assertEquals(value, accessor.getStatus());
  }

  @Test
  void testAccessConfiguration() throws Exception {
    AddIntegrationDto accessor = new AddIntegrationDto();
    assertSame(accessor, accessor.setConfiguration("testString"));
    assertEquals("testString", accessor.getConfiguration());
  }
}
