package sh.trishul.integration.communication.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import org.junit.jupiter.api.Test;
import sh.trishul.base.types.base.pojo.Refresher;
import sh.trishul.communication.model.channel.ChannelType;
import sh.trishul.integration.model.Integration;
import sh.trishul.integration.model.IntegrationAccessor;
import sh.trishul.model.base.pojo.refresher.accessor.AccessorRefresher;

class IntegrationCommunicationConfigMapperTest {

  @Test
  void testFromDto_MapsIdOnly() {
    IntegrationCommunicationConfig config
        = IntegrationCommunicationConfigMapper.INSTANCE.fromDto(7L);

    assertEquals(7L, config.getId());
    assertNull(config.getIntegration());
  }

  @Test
  void testFromAddDto_MapsFields() {
    AddIntegrationCommunicationConfigDto dto = new AddIntegrationCommunicationConfigDto()
        .setIntegrationId(2L).setChannelType(ChannelType.EMAIL).setChannelAddress("address")
        .setDefaultFrom("from").setEnabled(true);

    IntegrationCommunicationConfig config
        = IntegrationCommunicationConfigMapper.INSTANCE.fromAddDto(dto);

    assertNull(config.getId());
    assertEquals(2L, config.getIntegration().getId());
    assertEquals(ChannelType.EMAIL, config.getChannelType());
    assertEquals("address", config.getChannelAddress());
    assertEquals("from", config.getDefaultFrom());
    assertEquals(true, config.getEnabled());
  }

  @Test
  void testFromUpdateDto_MapsFields() {
    UpdateIntegrationCommunicationConfigDto dto = new UpdateIntegrationCommunicationConfigDto()
        .setId(1L).setIntegrationId(2L).setChannelType(ChannelType.SMS).setChannelAddress("address")
        .setDefaultFrom("from").setEnabled(false).setVersion(3);

    IntegrationCommunicationConfig config
        = IntegrationCommunicationConfigMapper.INSTANCE.fromUpdateDto(dto);

    assertEquals(1L, config.getId());
    assertEquals(2L, config.getIntegration().getId());
    assertEquals(ChannelType.SMS, config.getChannelType());
    assertEquals("address", config.getChannelAddress());
    assertEquals("from", config.getDefaultFrom());
    assertEquals(false, config.getEnabled());
    assertEquals(3, config.getVersion());
  }

  @Test
  void testToDto_MapsFields() {
    IntegrationCommunicationConfig config = new IntegrationCommunicationConfig(1L,
        new Integration(2L), ChannelType.WHATSAPP, "address", "from", true,
        LocalDateTime.of(2024, 1, 1, 0, 0), LocalDateTime.of(2024, 1, 2, 0, 0), 3);

    IntegrationCommunicationConfigDto dto
        = IntegrationCommunicationConfigMapper.INSTANCE.toDto(config);

    assertEquals(1L, dto.getId());
    assertEquals(2L, dto.getIntegration().getId());
    assertEquals(ChannelType.WHATSAPP, dto.getChannelType());
    assertEquals("address", dto.getChannelAddress());
    assertEquals("from", dto.getDefaultFrom());
    assertEquals(true, dto.getEnabled());
    assertEquals(LocalDateTime.of(2024, 1, 1, 0, 0), dto.getCreatedAt());
    assertEquals(LocalDateTime.of(2024, 1, 2, 0, 0), dto.getLastUpdated());
    assertEquals(3, dto.getVersion());
  }

  @Test
  void testRefresher_DelegatesToNestedRefreshers() {
    @SuppressWarnings("unchecked")
    AccessorRefresher<Long, IntegrationCommunicationConfigAccessor<?>, IntegrationCommunicationConfig> refresher
        = mock(AccessorRefresher.class);
    @SuppressWarnings("unchecked")
    Refresher<Integration, IntegrationAccessor<?>> integrationRefresher = mock(Refresher.class);

    IntegrationCommunicationConfigRefresher configRefresher
        = new IntegrationCommunicationConfigRefresher(refresher, integrationRefresher);
    IntegrationCommunicationConfigAccessor<?> accessor
        = mock(IntegrationCommunicationConfigAccessor.class);
    IntegrationCommunicationConfig config = new IntegrationCommunicationConfig(1L);
    Collection<IntegrationCommunicationConfig> configs = List.of(config);

    configRefresher.refresh(configs);
    configRefresher.refreshAccessors(List.of(accessor));

    @SuppressWarnings("unchecked")
    Collection<? extends IntegrationAccessor<?>> expectedConfigs
        = (Collection<? extends IntegrationAccessor<?>>) (Collection<?>) configs;
    verify(integrationRefresher, times(1)).refreshAccessors(expectedConfigs);
    verify(refresher, times(1)).refreshAccessors(List.of(accessor));
  }
}
