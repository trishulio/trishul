package io.trishul.crud.service;

import javax.persistence.OptimisticLockException;

import io.trishul.model.base.pojo.Versioned;

public class LockService {
    public void optimisticLockCheck(Versioned original, Versioned update) {
        if (!original.getVersion().equals(update.getVersion())) {
            throw new OptimisticLockException(String.format("Cannot update entity of version: %s with update payload of version: %s", original.getVersion(), update.getVersion()));
        }
    }
}
