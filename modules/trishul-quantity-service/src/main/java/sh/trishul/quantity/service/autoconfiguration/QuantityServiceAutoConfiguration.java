package sh.trishul.quantity.service.autoconfiguration;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import sh.trishul.quantity.service.unit.repository.QuantityUnitRepository;
import sh.trishul.quantity.service.unit.service.QuantityUnitService;
import sh.trishul.quantity.service.unit.service.QuantityUnitServiceImpl;

@Configuration
public class QuantityServiceAutoConfiguration {

  @Bean
  @ConditionalOnMissingBean
  public QuantityUnitService quantityUnitService(QuantityUnitRepository quantityUnitRepository) {
    return new QuantityUnitServiceImpl(quantityUnitRepository);
  }
}
