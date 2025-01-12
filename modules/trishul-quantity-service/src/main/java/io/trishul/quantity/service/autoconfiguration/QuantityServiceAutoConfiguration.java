package io.trishul.quantity.service.autoconfiguration;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.trishul.quantity.service.unit.repository.QuantityUnitRepository;
import io.trishul.quantity.service.unit.service.QuantityUnitService;
import io.trishul.quantity.service.unit.service.QuantityUnitServiceImpl;

@Configuration
public class QuantityServiceAutoConfiguration {

  @Bean
  @ConditionalOnMissingBean
  public QuantityUnitService quantityUnitService(QuantityUnitRepository quantityUnitRepository) {
    return new QuantityUnitServiceImpl(quantityUnitRepository);
  }
}
