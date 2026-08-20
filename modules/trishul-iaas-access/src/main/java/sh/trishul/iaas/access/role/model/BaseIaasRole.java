package sh.trishul.iaas.access.role.model;

import java.time.LocalDateTime;

public interface BaseIaasRole<T extends BaseIaasRole<T>> {
  String ATTR_NAME = "name";
  String ATTR_DESCRIPTION = "description";
  String ATTR_ASSUME_POLICY_DOCUMENT = "assumePolicyDocument";
  String ATTR_IAAS_RESOURCE_NAME = "iaasResourceName";
  String ATTR_IAAS_ID = "iaasId";
  String ATTR_LAST_USED = "lastUsed";

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
