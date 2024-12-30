package io.trishul.iaas.access.role.model;

import java.time.LocalDateTime;

public interface BaseIaasRole<T extends BaseIaasRole<T>> {
  final String ATTR_NAME = "name";
  final String ATTR_DESCRIPTION = "description";
  final String ATTR_ASSUME_POLICY_DOCUMENT = "assumePolicyDocument";
  final String ATTR_IAAS_RESOURCE_NAME = "iaasResourceName";
  final String ATTR_IAAS_ID = "iaasId";
  final String ATTR_LAST_USED = "lastUsed";

  String getName();

  T setName(String name);

  String getDescription();

  T setDescription(String description);

  String getAssumePolicyDocument();

  T setAssumePolicyDocument(String assumePolicyDocument);

  String getIaasResourceName();

  T setIaasResourceName(String iaasResourceName);

  String getIaasId();

  T setIaasId(String iaasId);

  LocalDateTime getLastUsed();

  T setLastUsed(LocalDateTime lastUsed);
}
