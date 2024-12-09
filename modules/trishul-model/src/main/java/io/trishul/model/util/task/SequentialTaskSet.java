package io.trishul.model.util.task;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class SequentialTaskSet implements TaskSet {
    private final List<Exception> errors;
    private final List<TaskResult<?>> results;

    public SequentialTaskSet() {
        this.errors = new ArrayList<>();
        this.results = new ArrayList<>();
    }

    @Override
    public <T> void submit(Supplier<T> supplier) {
        try {
            T ret = supplier.get();
            TaskResult<T> result = new TaskResultImpl<T>(ret);
            results.add(result);
        } catch (Exception e) {
            errors.add(e);
        }
    }

    @Override
    public <T> void submit(Runnable runnable) {
        try {
            runnable.run();
            results.add(null);
        } catch (Exception e) {
            errors.add(e);
        }
    }

    @Override
    public List<Exception> getErrors() {
        return this.errors;
    }

    @Override
    public List<TaskResult<?>> getResults() {
        return this.results;
    }
}
