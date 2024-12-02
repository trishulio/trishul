package io.trishul.model.util.task;

public class TaskResultImpl<T> implements TaskResult<T> {
    private T returnValue;

    public TaskResultImpl(T returnValue) {
        this.returnValue = returnValue;
    }

    @Override
    public T getReturnValue() {
        return returnValue;
    }
}
