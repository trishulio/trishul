package io.trishul.model.executor;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.function.Supplier;

public class BlockingAsyncExecutor {
  // Note: Add fault-tolerance to this executor using resilience4j library.
  // if the need arises.

  public <R> List<R> supply(List<Supplier<R>> suppliers) {
    @SuppressWarnings("unchecked")
    CompletableFuture<R>[] operations = new CompletableFuture[suppliers.size()];
    for (int i = 0; i < suppliers.size(); i++) {
      operations[i] = CompletableFuture.supplyAsync(suppliers.get(i));
    }

    CompletableFuture<List<R>> resultOperation = CompletableFuture.allOf(operations)
        .thenApply(__ -> Arrays.stream(operations).map(CompletableFuture::join).toList());

    try {
      return resultOperation.get();
    } catch (ExecutionException | InterruptedException e) {
      throw new RuntimeException(
          String.format("Failed to execute tasks because: %s", e.getMessage()), e);
    }
  }

  public void run(List<Runnable> runnables) {
    @SuppressWarnings("unchecked")
    CompletableFuture<Void>[] operations = new CompletableFuture[runnables.size()];
    for (int i = 0; i < runnables.size(); i++) {
      operations[i] = CompletableFuture.runAsync(runnables.get(i));
    }

    CompletableFuture<Void> resultOperation = CompletableFuture.allOf(operations);

    try {
      resultOperation.get();
    } catch (ExecutionException | InterruptedException e) {
      throw new RuntimeException(
          String.format("Failed to execute tasks because: %s", e.getMessage()), e);
    }
  }
}
