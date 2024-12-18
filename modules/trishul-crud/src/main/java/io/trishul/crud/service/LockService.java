package io.trishul.crud.service;

import io.trishul.base.types.base.pojo.Versioned;
import javax.persistence.OptimisticLockException;

public class LockService {
    public void optimisticLockCheck(Versioned original, Versioned update) {
        if (original.getVersion()
                != update.getVersion()) { // todo: changing this to equals throws error during tests
            throw new OptimisticLockException(
                    String.format(
                            "Cannot update entity of version: %s with update payload of version: %s",
                            original.getVersion(), update.getVersion()));
        }
    }
}
