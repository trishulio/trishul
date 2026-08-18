package sh.trishul.integration.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;

class IntegrationMapperTest {

  @Test
  void testFromDto_MapsIdOnly() {
    Integration integration = IntegrationMapper.INSTANCE.fromDto(7L);

    assertEquals(7L, integration.getId());
    assertNull(integration.getName());
    assertNull(integration.getType());
  }

  @Test
  void testFromAddDto_MapsFields() {
    AddIntegrationDto addDto
        = new AddIntegrationDto().setName("name").setType(IntegrationType.COMMUNICATION)
            .setProvider("provider").setStatus(IntegrationStatus.ACTIVE).setConfiguration("config");

    Integration integration = IntegrationMapper.INSTANCE.fromAddDto(addDto);

    assertNull(integration.getId());
    assertEquals("name", integration.getName());
    assertEquals(IntegrationType.COMMUNICATION, integration.getType());
    assertEquals("provider", integration.getProvider());
    assertEquals(IntegrationStatus.ACTIVE, integration.getStatus());
    assertEquals("config", integration.getConfiguration());
    assertNull(integration.getCreatedAt());
    assertNull(integration.getLastUpdated());
  }

  @Test
  void testFromUpdateDto_MapsFields() {
    UpdateIntegrationDto updateDto = new UpdateIntegrationDto().setId(7L).setName("name")
        .setType(IntegrationType.STORAGE).setProvider("provider").setStatus(IntegrationStatus.ERROR)
        .setConfiguration("config").setVersion(2);

    Integration integration = IntegrationMapper.INSTANCE.fromUpdateDto(updateDto);

    assertEquals(7L, integration.getId());
    assertEquals("name", integration.getName());
    assertEquals(IntegrationType.STORAGE, integration.getType());
    assertEquals("provider", integration.getProvider());
    assertEquals(IntegrationStatus.ERROR, integration.getStatus());
    assertEquals("config", integration.getConfiguration());
    assertEquals(2, integration.getVersion());
    assertNull(integration.getCreatedAt());
    assertNull(integration.getLastUpdated());
  }

  @Test
  void testToDto_MapsFields() {
    Integration integration = new Integration(7L, "name", IntegrationType.PAYMENT, "provider",
        IntegrationStatus.PENDING, "config", LocalDateTime.of(2024, 1, 1, 0, 0),
        LocalDateTime.of(2024, 1, 2, 0, 0), 3);

    IntegrationDto dto = IntegrationMapper.INSTANCE.toDto(integration);

    assertEquals(7L, dto.getId());
    assertEquals("name", dto.getName());
    assertEquals(IntegrationType.PAYMENT, dto.getType());
    assertEquals("provider", dto.getProvider());
    assertEquals(IntegrationStatus.PENDING, dto.getStatus());
    assertEquals("config", dto.getConfiguration());
    assertEquals(LocalDateTime.of(2024, 1, 1, 0, 0), dto.getCreatedAt());
    assertEquals(LocalDateTime.of(2024, 1, 2, 0, 0), dto.getLastUpdated());
    assertEquals(3, dto.getVersion());
  }
}
