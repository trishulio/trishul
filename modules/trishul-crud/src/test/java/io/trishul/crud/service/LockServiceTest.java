package io.trishul.crud.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import jakarta.persistence.OptimisticLockException;

public class LockServiceTest {
  private LockService lockService;

  @BeforeEach
  public void init() {
    this.lockService = new LockService();
  }

  @Test
  public void testOptimisticLockCheck_Returns_WhenOriginalAndUpdateAreNull() {
    lockService.optimisticLockCheck(() -> null, () -> null);
  }

  @Test
  public void testOptimisticLockCheck_Returns_WhenOriginalAndUpdateAreNotNullAndEqual() {
    lockService.optimisticLockCheck(() -> 1, () -> 1);
  }

  @Test
  public void testOptimisticLockCheck_ThrowsOptimisticLockException_WhenOriginalIsNullButUpdateIsNotNull() {
    OptimisticLockException exception = assertThrows(OptimisticLockException.class, () -> {
      lockService.optimisticLockCheck(() -> null, () -> 1);
    });

    assertEquals("Cannot update entity of version: null with update payload of version: 1",
        exception.getMessage());
  }

  @Test
  public void testOptimisticLockCheck_ThrowsOptimisticLockException_WhenOriginalIsNotNullButUpdateIsNull() {
    OptimisticLockException exception = assertThrows(OptimisticLockException.class, () -> {
      lockService.optimisticLockCheck(() -> 1, () -> null);
    });

    assertEquals("Cannot update entity of version: 1 with update payload of version: null",
        exception.getMessage());
  }

  @Test
  public void testOptimisticLockCheck_ThrowsOptimisticLockException_WhenOriginalVersionIsNotEqualToUpdateVersion() {
    OptimisticLockException exception = assertThrows(OptimisticLockException.class, () -> {
      lockService.optimisticLockCheck(() -> 1, () -> 2);
    });

    assertEquals("Cannot update entity of version: 1 with update payload of version: 2",
        exception.getMessage());
  }
}
