package io.trishul.quantity.service.unit.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import io.trishul.quantity.service.unit.repository.QuantityUnitRepository;
import io.trishul.quantity.unit.SupportedUnits;
import io.trishul.quantity.unit.UnitEntity;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Optional;
import java.util.TreeSet;
import javax.measure.Unit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public class QuantityUnitServiceImplTest {
  private QuantityUnitService quantityUnitService;

  private QuantityUnitRepository quantityUnitRepositoryMock;

  @BeforeEach
  public void init() {
    quantityUnitRepositoryMock = mock(QuantityUnitRepository.class);

    quantityUnitService = new QuantityUnitServiceImpl(quantityUnitRepositoryMock);
  }

  @Test
  public void testGetRoles_returnsRoles() throws Exception {
    Page<UnitEntity> expectedUnitsPage = new PageImpl<>(List.of(new UnitEntity("g", "g")));

    final ArgumentCaptor<Specification<UnitEntity>> specificationCaptor
        = ArgumentCaptor.forClass(Specification.class);

    when(quantityUnitRepositoryMock.findAll(specificationCaptor.capture(),
        eq(PageRequest.of(0, 100, Sort.by(Direction.ASC, new String[] {"id"})))))
            .thenReturn(expectedUnitsPage);

    Page<UnitEntity> actualUnitsPage
        = quantityUnitService.getUnits(null, new TreeSet<>(List.of("id")), true, 0, 100);

    assertEquals(List.of(new UnitEntity("g", "g")), actualUnitsPage.getContent());

    // TODO: Pending testing for the specification
    // specificationCaptor.getValue();
  }

  @Test
  public void testGetQuantityUnit_returnsUnit() throws Exception {
    String symbol = "g";
    UnitEntity unitEntity = new UnitEntity("g");
    Optional<UnitEntity> expectedUnitEntity = Optional.ofNullable(unitEntity);

    when(quantityUnitRepositoryMock.findBySymbol(symbol)).thenReturn(expectedUnitEntity);

    Unit<?> returnedUnit = quantityUnitService.get(symbol);

    assertEquals(SupportedUnits.GRAM, returnedUnit);
  }

  @Test
  public void testQuantityUnitService_classIsTransactional() throws Exception {
    Transactional transactional = quantityUnitService.getClass().getAnnotation(Transactional.class);

    assertNotNull(transactional);
    assertEquals(transactional.isolation(), Isolation.DEFAULT);
    assertEquals(transactional.propagation(), Propagation.REQUIRED);
  }

  @Test
  public void testQuantityUnitService_methodsAreNotTransactional() throws Exception {
    Method[] methods = quantityUnitService.getClass().getMethods();
    for (Method method : methods) {
      assertFalse(method.isAnnotationPresent(Transactional.class));
    }
  }
}
