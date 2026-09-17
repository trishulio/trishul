package sh.trishul.quantity.service.unit.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
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
import sh.trishul.quantity.service.unit.repository.QuantityUnitRepository;
import sh.trishul.quantity.unit.SupportedUnits;
import sh.trishul.quantity.unit.UnitEntity;

class QuantityUnitServiceImplTest {
  private QuantityUnitService quantityUnitService;

  private QuantityUnitRepository quantityUnitRepositoryMock;

  @BeforeEach
  void init() {
    quantityUnitRepositoryMock = mock(QuantityUnitRepository.class);

    quantityUnitService = new QuantityUnitServiceImpl(quantityUnitRepositoryMock);
  }

  @Test
  void testGetRoles_returnsRoles() throws Exception {
    Page<UnitEntity> expectedUnitsPage = new PageImpl<>(List.of(new UnitEntity("g", "g")));

    final ArgumentCaptor<Specification<UnitEntity>> specificationCaptor
        = ArgumentCaptor.forClass(Specification.class);

    when(quantityUnitRepositoryMock.findAll(specificationCaptor.capture(),
        eq(PageRequest.of(0, 100, Sort.by(Direction.ASC, new String[] {"id"})))))
        .thenReturn(expectedUnitsPage);

    Page<UnitEntity> actualUnitsPage
        = quantityUnitService.getUnits(null, new TreeSet<>(List.of("id")), true, 0, 100);

    assertEquals(List.of(new UnitEntity("g", "g")), actualUnitsPage.getContent());
  }

  @Test
  void testGetQuantityUnit_returnsUnit() throws Exception {
    String symbol = "g";
    UnitEntity unitEntity = new UnitEntity("g");
    Optional<UnitEntity> expectedUnitEntity = Optional.ofNullable(unitEntity);

    when(quantityUnitRepositoryMock.findBySymbol(symbol)).thenReturn(expectedUnitEntity);

    Unit<?> returnedUnit = quantityUnitService.get(symbol);

    assertEquals(SupportedUnits.GRAM, returnedUnit);
  }

  @Test
  void testQuantityUnitService_classIsTransactional() throws Exception {
    Transactional transactional = quantityUnitService.getClass().getAnnotation(Transactional.class);

    assertNotNull(transactional);
    assertEquals(transactional.isolation(), Isolation.DEFAULT);
    assertEquals(transactional.propagation(), Propagation.REQUIRED);
  }

  @Test
  void testQuantityUnitService_methodsAreNotTransactional() throws Exception {
    Method[] methods = quantityUnitService.getClass().getMethods();
    for (Method method : methods) {
      assertFalse(method.isAnnotationPresent(Transactional.class));
    }
  }

  @Test
  @SuppressWarnings("unchecked")
  void testSearch_ReturnsUnitsFromGetUnits_WhenQueryIsNull() {
    Page<UnitEntity> expectedUnitsPage = new PageImpl<>(List.of(new UnitEntity("g", "g")));
    when(quantityUnitRepositoryMock.findAll(any(Specification.class),
        eq(PageRequest.of(0, 100, Sort.by(Direction.ASC, "symbol")))))
        .thenReturn(expectedUnitsPage);

    Page<UnitEntity> actualUnitsPage
        = quantityUnitService.search(null, new TreeSet<>(List.of("symbol")), true, 0, 100);

    assertEquals(expectedUnitsPage, actualUnitsPage);
  }

  @Test
  @SuppressWarnings("unchecked")
  void testSearch_ReturnsUnitsFromGetUnits_WhenQueryIsBlank() {
    Page<UnitEntity> expectedUnitsPage = new PageImpl<>(List.of(new UnitEntity("g", "g")));
    when(quantityUnitRepositoryMock.findAll(any(Specification.class),
        eq(PageRequest.of(0, 100, Sort.by(Direction.ASC, "symbol")))))
        .thenReturn(expectedUnitsPage);

    Page<UnitEntity> actualUnitsPage
        = quantityUnitService.search("   ", new TreeSet<>(List.of("symbol")), true, 0, 100);

    assertEquals(expectedUnitsPage, actualUnitsPage);
  }

  @Test
  @SuppressWarnings("unchecked")
  void testSearch_ReturnsFilteredUnits_WhenQueryIsNotBlank() {
    Page<UnitEntity> expectedUnitsPage = new PageImpl<>(List.of(new UnitEntity("g", "g")));
    ArgumentCaptor<Specification<UnitEntity>> specCaptor
        = ArgumentCaptor.forClass(Specification.class);

    when(quantityUnitRepositoryMock.findAll(specCaptor.capture(),
        eq(PageRequest.of(0, 100, Sort.by(Direction.ASC, "symbol")))))
        .thenReturn(expectedUnitsPage);

    Page<UnitEntity> actualUnitsPage
        = quantityUnitService.search("gram", new TreeSet<>(List.of("symbol")), true, 0, 100);

    assertEquals(expectedUnitsPage, actualUnitsPage);

    Root<UnitEntity> root = mock(Root.class);
    CriteriaQuery<?> cq = mock(CriteriaQuery.class);
    CriteriaBuilder cb = mock(CriteriaBuilder.class);
    Path pathSymbol = mock(Path.class);
    Path pathName = mock(Path.class);
    Expression<String> lowerSymbol = mock(Expression.class);
    Expression<String> lowerName = mock(Expression.class);
    Predicate predSymbol = mock(Predicate.class);
    Predicate predName = mock(Predicate.class);
    Predicate orPred = mock(Predicate.class);

    when(root.get(UnitEntity.FIELD_SYMBOL)).thenReturn(pathSymbol);
    when(root.get(UnitEntity.FIELD_NAME)).thenReturn(pathName);
    when(cb.lower(pathSymbol)).thenReturn(lowerSymbol);
    when(cb.lower(pathName)).thenReturn(lowerName);
    when(cb.like(lowerSymbol, "%gram%")).thenReturn(predSymbol);
    when(cb.like(lowerName, "%gram%")).thenReturn(predName);
    when(cb.or(predSymbol, predName)).thenReturn(orPred);

    Predicate actualPred = specCaptor.getValue().toPredicate(root, cq, cb);
    assertEquals(orPred, actualPred);
  }
}
