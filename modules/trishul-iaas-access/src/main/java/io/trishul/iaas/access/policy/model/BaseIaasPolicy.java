package io.trishul.iaas.access.policy.model;

public interface BaseIaasPolicy<T extends BaseIaasPolicy<T>> {
  final String ATTR_NAME = "name";
  final String ATTR_DOCUMENT = "document";
  final String ATTR_DESCRIPTION = "description";
  final String ATTR_IAAS_ID = "iaasId";
  final String ATTR_IAAS_RESOURCE_NAME = "iaasResourceName";

  String getName();

  T setName(String name);

  String getDocument();

  T setDocument(String document);

  String getDescription();

  T setDescription(String description);

  String getIaasId();

  T setIaasId(String iaasId);

  String getIaasResourceName();

  T setIaasResourceName(String iaasResourceName);
}
