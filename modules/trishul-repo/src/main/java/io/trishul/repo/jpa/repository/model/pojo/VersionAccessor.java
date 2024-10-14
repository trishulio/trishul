package io.trishul.repo.jpa.repository.model.pojo;

public interface VersionAccessor extends Versioned {
    void setVersion(Integer version);
}
