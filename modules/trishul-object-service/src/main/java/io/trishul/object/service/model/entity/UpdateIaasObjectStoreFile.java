package io.trishul.object.service.model.entity;

import java.net.URI;

import io.trishul.repo.jpa.repository.model.pojo.UpdatableEntity;

public interface UpdateIaasObjectStoreFile extends BaseIaasObjectStoreFile, UpdatableEntity<URI> {
}
