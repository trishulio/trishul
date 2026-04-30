package io.trishul.crud.autoconfiguration;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import io.trishul.crud.controller.filter.AttributeFilter;
import io.trishul.crud.service.LockService;
import io.trishul.model.executor.BlockingAsyncExecutor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CrudAutoConfigurationTest {
  private CrudAutoConfiguration config;

  @BeforeEach
  void init() {
    config = new CrudAutoConfiguration();
  }

  @Test
  void testLockService_ReturnsNonNullInstance() {
    LockService lockService = config.lockService();
    assertNotNull(lockService);
  }

  @Test
  void testLockService_ReturnsInstanceOfLockService() {
    LockService lockService = config.lockService();
    assertTrue(lockService instanceof LockService);
  }

  @Test
  void testBlockingAsyncExecutor_ReturnsNonNullInstance() {
    BlockingAsyncExecutor executor = config.blockingAsyncExecutor();
    assertNotNull(executor);
  }

  @Test
  void testBlockingAsyncExecutor_ReturnsInstanceOfBlockingAsyncExecutor() {
    BlockingAsyncExecutor executor = config.blockingAsyncExecutor();
    assertTrue(executor instanceof BlockingAsyncExecutor);
  }

  @Test
  void testAttributeFilter_ReturnsNonNullInstance() {
    AttributeFilter filter = config.attributeFilter();
    assertNotNull(filter);
  }

  @Test
  void testAttributeFilter_ReturnsInstanceOfAttributeFilter() {
    AttributeFilter filter = config.attributeFilter();
    assertTrue(filter instanceof AttributeFilter);
  }
}
