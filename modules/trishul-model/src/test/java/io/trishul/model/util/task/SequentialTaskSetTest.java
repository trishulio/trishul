package io.trishul.model.util.task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import java.util.function.Supplier;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SequentialTaskSetTest {
  private TaskSet tasks;

  @BeforeEach
  public void init() {
    this.tasks = new SequentialTaskSet();
  }

  @Test
  public void testSubmitSupplier_ExecutesTheSupplierAndStoreResult_WhenSupplierRunsWithoutErrors() {
    tasks.submit(() -> "RETURN_VALUE");

    assertEquals(1, tasks.getResults().size());
    assertEquals("RETURN_VALUE", tasks.getResults().get(0).getReturnValue());
    assertEquals(0, tasks.getErrors().size());
  }

  @Test
  public void testSubmit_ExecutesTheSupplierAndStoreException_WhenSupplierThrowsException() {
    RuntimeException e = new RuntimeException();
    tasks.submit(new Supplier<Void>() {
      @Override
      public Void get() {
        throw e;
      }
    });

    assertEquals(0, tasks.getResults().size());
    assertEquals(1, tasks.getErrors().size());
    assertEquals(e, tasks.getErrors().get(0));
  }

  @Test
  public void testSubmitRunnable_ExecutesTheRunnableAndStoreNullResult_WhenRunnableRunsWithoutErrors() {
    tasks.submit(() -> {
    });

    assertEquals(1, tasks.getResults().size());
    assertNull(tasks.getResults().get(0));
    assertEquals(0, tasks.getErrors().size());
  }

  @Test
  public void testSubmitRunnable_ExecutesTheRunnablerAndStoreException_WhenRunnableThrowsException() {
    RuntimeException e = new RuntimeException();
    tasks.submit(new Runnable() {
      @Override
      public void run() {
        throw e;
      }
    });

    assertEquals(0, tasks.getResults().size());
    assertEquals(1, tasks.getErrors().size());
    assertEquals(e, tasks.getErrors().get(0));
  }
}
