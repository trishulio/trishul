package io.trishul.quantity.service.autoconfiguration;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.trishul.quantity.service.unit.repository.QuantityUnitRepository;
import io.trishul.quantity.service.unit.service.QuantityUnitService;

class QuantityServiceAutoConfigurationTest {

  private QuantityServiceAutoConfiguration config;

  @BeforeEach
  void setUp() {
    config = new QuantityServiceAutoConfiguration();
  }

  @Test
  void testQuantityUnitService_ReturnsNonNull() {
    QuantityUnitRepository mockRepository = mock(QuantityUnitRepository.class);

    QuantityUnitService result = config.quantityUnitService(mockRepository);

    assertNotNull(result);
  }
}
