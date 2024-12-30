package io.trishul.base.types.util.task;

import java.util.List;
import java.util.function.Supplier;

public interface TaskSet {
  <T> void submit(Supplier<T> supplier);

  <T> void submit(Runnable runnable);

  List<Exception> getErrors();

  List<TaskResult<?>> getResults();
}
