package io.trishul.model.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.fail;
import io.trishul.base.types.lambda.CheckedRunnable;
import io.trishul.model.validator.UtilityProvider;
import io.trishul.model.validator.Validator;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicInteger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ThreadLocalUtilityProviderTest {
  private UtilityProvider provider;

  @BeforeEach
  public void init() {
    provider = new ThreadLocalUtilityProvider();
  }

  @Test
  public void testAccessValidator() {
    Validator v1 = new Validator();
    provider.setValidator(v1);

    assertSame(v1, provider.getValidator());
  }

  @Test
  public void testAccessValidator_ReturnsThreadedInstance()
      throws InterruptedException, ExecutionException {
    AtomicInteger count = new AtomicInteger(0);

    CompletableFuture<Void> op1 = runAsync(() -> {
      Validator validator = new Validator();
      provider.setValidator(validator);
      Thread.sleep(75);
      assertSame(validator, provider.getValidator());
      count.incrementAndGet();
    });

    CompletableFuture<Void> op2 = runAsync(() -> {
      Validator validator = new Validator();
      provider.setValidator(validator);
      Thread.sleep(50);
      assertSame(validator, provider.getValidator());
      count.incrementAndGet();
    });

    CompletableFuture<Void> op3 = runAsync(() -> {
      Validator validator = new Validator();
      provider.setValidator(validator);
      Thread.sleep(25);
      assertSame(validator, provider.getValidator());
      count.incrementAndGet();
    });

    CompletableFuture.allOf(op1, op2, op3).get();
    assertEquals(3, count.get());
  }

  private CompletableFuture<Void> runAsync(CheckedRunnable<Exception> runnable) {
    CompletableFuture<Void> marker = new CompletableFuture<>();
    new Thread(() -> {
      try {
        runnable.run();
        marker.complete(null);
      } catch (Exception e) {
        fail();
      }
    }).start();

    return marker;
  }
}
