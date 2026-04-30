package io.trishul.crud.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import io.trishul.model.reflection.ReflectionManipulator;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BaseServiceTest {
  private BaseService service;
  private ReflectionManipulator mUtil;

  @BeforeEach
  void init() {
    mUtil = mock(ReflectionManipulator.class);
    service = new BaseService(mUtil);
  }

  @Test
  void testDefaultConstructor_CreatesService() {
    BaseService defaultService = new BaseService();
    assertNotNull(defaultService);
  }

  @Test
  void testGetPropertyNames_WithoutExclusions_DelegatesToUtil() {
    Set<String> expectedProps = Set.of("prop1", "prop2");
    when(mUtil.getPropertyNames(String.class, null)).thenReturn(expectedProps);

    Set<String> result = service.getPropertyNames(String.class);

    assertEquals(expectedProps, result);
  }

  @Test
  void testGetPropertyNames_WithExclusions_DelegatesToUtil() {
    Set<String> exclusions = Set.of("excluded");
    Set<String> expectedProps = Set.of("prop1", "prop2");
    when(mUtil.getPropertyNames(String.class, exclusions)).thenReturn(expectedProps);

    Set<String> result = service.getPropertyNames(String.class, exclusions);

    assertEquals(expectedProps, result);
  }

  @Test
  void testGetPropertyNames_ReturnsNonNullSet() {
    when(mUtil.getPropertyNames(String.class, null)).thenReturn(Set.of());

    Set<String> result = service.getPropertyNames(String.class);

    assertNotNull(result);
    assertTrue(result.isEmpty());
  }
}
