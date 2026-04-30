package io.trishul.crud.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import io.trishul.crud.controller.filter.AttributeFilter;
import io.trishul.model.base.dto.BaseDto;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BaseControllerTest {
  private TestableBaseController controller;
  private AttributeFilter mFilter;

  static class TestableBaseController extends BaseController {
    public TestableBaseController() {
      super();
    }

    public TestableBaseController(AttributeFilter filter) {
      super(filter);
    }
  }

  static class TestDto extends BaseDto {
    private String value;

    public String getValue() {
      return value;
    }

    public void setValue(String value) {
      this.value = value;
    }
  }

  @BeforeEach
  void init() {
    mFilter = mock(AttributeFilter.class);
    controller = new TestableBaseController(mFilter);
  }

  @Test
  void testDefaultConstructor_CreatesController() {
    TestableBaseController defaultController = new TestableBaseController();
    assertNotNull(defaultController);
  }

  @Test
  void testFilter_DoesNotCallRetain_WhenRetainAttrIsNull() {
    TestDto dto = new TestDto();
    dto.setValue("value");

    controller.filter(dto, null);

    verifyNoInteractions(mFilter);
  }

  @Test
  void testFilter_DoesNotCallRetain_WhenRetainAttrIsEmpty() {
    TestDto dto = new TestDto();
    dto.setValue("value");

    controller.filter(dto, new HashSet<>());

    verifyNoInteractions(mFilter);
  }

  @Test
  void testFilter_CallsRetain_WhenRetainAttrIsNotEmpty() {
    TestDto dto = new TestDto();
    dto.setValue("value");
    Set<String> retainAttr = Set.of("value");

    controller.filter(dto, retainAttr);

    verify(mFilter).retain(dto, retainAttr);
  }

  @Test
  void testConstants_HaveExpectedValues() {
    assertEquals("sort", BaseController.PROPNAME_SORT_BY);
    assertEquals("order_asc", BaseController.PROPNAME_ORDER_ASC);
    assertEquals("page", BaseController.PROPNAME_PAGE_INDEX);
    assertEquals("size", BaseController.PROPNAME_PAGE_SIZE);
    assertEquals("attr", BaseController.PROPNAME_ATTR);
    assertEquals("id", BaseController.VALUE_DEFAULT_SORT_BY);
    assertEquals("true", BaseController.VALUE_DEFAULT_ORDER_ASC);
    assertEquals("0", BaseController.VALUE_DEFAULT_PAGE_INDEX);
    assertEquals("100", BaseController.VALUE_DEFAULT_PAGE_SIZE);
    assertEquals("", BaseController.VALUE_DEFAULT_ATTR);
  }
}
