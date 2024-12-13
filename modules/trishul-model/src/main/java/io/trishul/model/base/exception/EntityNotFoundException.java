package io.trishul.model.base.exception;

import java.text.MessageFormat;

public class EntityNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public EntityNotFoundException(String msg) {
        super(msg);
    }

    public EntityNotFoundException(String entity, String entityId) {
        this(entity, "id", entityId);
    }

    public EntityNotFoundException(String entity, Object entityId) {
        this(entity, entityId == null ? null : entityId.toString());
    }

    public EntityNotFoundException(String entity, String field, String value) {
        this(MessageFormat.format("{0} not found with {1}: {2}", entity, field, value));
    }
}