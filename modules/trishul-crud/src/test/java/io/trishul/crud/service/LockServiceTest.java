package io.trishul.crud.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import jakarta.persistence.OptimisticLockException;

class LockServiceTest {
  private LockService lockService;

  @BeforeEach
  void init() {
    this.lockService = new LockService();
  }

  @Test
  void testOptimisticLockCheck_Returns_WhenOriginalAndUpdateAreNull() {
    lockService.optimisticLockCheck(() -> null, () -> null);
    // Test passes if no exception is thrown
    assertTrue(true, "Method completes without throwing exception");
  }

  @Test
  void testOptimisticLockCheck_Returns_WhenOriginalAndUpdateAreNotNullAndEqual() {
    lockService.optimisticLockCheck(() -> 1, () -> 1);
    // Test passes if no exception is thrown
    assertTrue(true, "Method completes without throwing exception");
  }

  @Test
  void testOptimisticLockCheck_ThrowsOptimisticLockException_WhenOriginalIsNullButUpdateIsNotNull() {
    OptimisticLockException exception = assertThrows(OptimisticLockException.class, () -> {
      lockService.optimisticLockCheck(() -> null, () -> 1);
    });

    assertEquals("Cannot update entity of version: null with update payload of version: 1",
        exception.getMessage());
  }

  @Test
  void testOptimisticLockCheck_ThrowsOptimisticLockException_WhenOriginalIsNotNullButUpdateIsNull() {
    OptimisticLockException exception = assertThrows(OptimisticLockException.class, () -> {
      lockService.optimisticLockCheck(() -> 1, () -> null);
    });

    assertEquals("Cannot update entity of version: 1 with update payload of version: null",
        exception.getMessage());
  }

  @Test
  void testOptimisticLockCheck_ThrowsOptimisticLockException_WhenOriginalVersionIsNotEqualToUpdateVersion() {
    OptimisticLockException exception = assertThrows(OptimisticLockException.class, () -> {
      lockService.optimisticLockCheck(() -> 1, () -> 2);
    });

    assertEquals("Cannot update entity of version: 1 with update payload of version: 2",
        exception.getMessage());
  }
}
