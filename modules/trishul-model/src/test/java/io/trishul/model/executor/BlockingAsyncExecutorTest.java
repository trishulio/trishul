package io.trishul.model.executor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BlockingAsyncExecutorTest {

  private BlockingAsyncExecutor executor;

  @BeforeEach
  void setUp() {
    executor = new BlockingAsyncExecutor();
  }

  @Test
  void testSupply_ReturnsEmptyList_WhenSuppliersIsEmpty() {
    List<Supplier<String>> suppliers = new ArrayList<>();

    List<String> result = executor.supply(suppliers);

    assertNotNull(result);
    assertTrue(result.isEmpty());
  }

  @Test
  void testSupply_ReturnsResults_WhenSingleSupplierProvided() {
    List<Supplier<String>> suppliers = List.of(() -> "result1");

    List<String> result = executor.supply(suppliers);

    assertEquals(1, result.size());
    assertEquals("result1", result.get(0));
  }

  @Test
  void testSupply_ReturnsAllResults_WhenMultipleSuppliersProvided() {
    List<Supplier<Integer>> suppliers = List.of(
        () -> 1,
        () -> 2,
        () -> 3
    );

    List<Integer> result = executor.supply(suppliers);

    assertEquals(3, result.size());
    assertTrue(result.contains(1));
    assertTrue(result.contains(2));
    assertTrue(result.contains(3));
  }

  @Test
  void testSupply_ExecutesSuppliersAsynchronously() {
    AtomicInteger counter = new AtomicInteger(0);
    List<Supplier<Integer>> suppliers = List.of(
        () -> { counter.incrementAndGet(); return 1; },
        () -> { counter.incrementAndGet(); return 2; },
        () -> { counter.incrementAndGet(); return 3; }
    );

    executor.supply(suppliers);

    assertEquals(3, counter.get());
  }

  @Test
  void testSupply_ThrowsRuntimeException_WhenSupplierThrowsException() {
    List<Supplier<String>> suppliers = List.of(
        () -> { throw new RuntimeException("Test exception"); }
    );

    RuntimeException exception = assertThrows(RuntimeException.class, () -> executor.supply(suppliers));
    assertTrue(exception.getMessage().contains("Failed to execute tasks because"));
  }

  @Test
  void testRun_CompletesSuccessfully_WhenRunnablesIsEmpty() {
    List<Runnable> runnables = new ArrayList<>();

    executor.run(runnables);
    // No exception thrown means success
  }

  @Test
  void testRun_ExecutesSingleRunnable() {
    AtomicInteger counter = new AtomicInteger(0);
    List<Runnable> runnables = List.of(() -> counter.incrementAndGet());

    executor.run(runnables);

    assertEquals(1, counter.get());
  }

  @Test
  void testRun_ExecutesMultipleRunnables() {
    AtomicInteger counter = new AtomicInteger(0);
    List<Runnable> runnables = List.of(
        () -> counter.incrementAndGet(),
        () -> counter.incrementAndGet(),
        () -> counter.incrementAndGet()
    );

    executor.run(runnables);

    assertEquals(3, counter.get());
  }

  @Test
  void testRun_ThrowsRuntimeException_WhenRunnableThrowsException() {
    List<Runnable> runnables = List.of(
        () -> { throw new RuntimeException("Test exception"); }
    );

    RuntimeException exception = assertThrows(RuntimeException.class, () -> executor.run(runnables));
    assertTrue(exception.getMessage().contains("Failed to execute tasks because"));
  }

  @Test
  void testRun_ExecutesRunnablesAsynchronously() {
    AtomicInteger counter = new AtomicInteger(0);
    List<Runnable> runnables = List.of(
        () -> { try { Thread.sleep(10); } catch (InterruptedException e) {} counter.incrementAndGet(); },
        () -> { try { Thread.sleep(10); } catch (InterruptedException e) {} counter.incrementAndGet(); },
        () -> { try { Thread.sleep(10); } catch (InterruptedException e) {} counter.incrementAndGet(); }
    );

    executor.run(runnables);

    assertEquals(3, counter.get());
  }
}
