package sh.trishul.crud.service;

import jakarta.persistence.OptimisticLockException;
import sh.trishul.base.types.base.pojo.Versioned;

public class LockService {
  public void optimisticLockCheck(Versioned original, Versioned update) {
    Integer originalVersion = original.getVersion();
    Integer updateVersion = update.getVersion();

    if (originalVersion == null && updateVersion == null) {
      return;
    }

    if (originalVersion == null || !originalVersion.equals(updateVersion)) {
      throw new OptimisticLockException(
          String.format("Cannot update entity of version: %s with update payload of version: %s",
              originalVersion, updateVersion));
    }
  }
}
