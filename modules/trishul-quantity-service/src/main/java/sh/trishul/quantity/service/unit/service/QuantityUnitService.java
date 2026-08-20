package sh.trishul.quantity.service.unit.service;

import java.util.Set;
import java.util.SortedSet;
import javax.measure.Unit;
import org.springframework.data.domain.Page;
import sh.trishul.quantity.unit.UnitEntity;

public interface QuantityUnitService {
  Page<UnitEntity> getUnits(Set<String> symbols, SortedSet<String> sort, boolean orderAscending,
      int page, int size);

  Unit<?> get(String symbol);
}
