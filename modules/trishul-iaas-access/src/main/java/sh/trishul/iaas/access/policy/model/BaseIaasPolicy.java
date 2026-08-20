package sh.trishul.iaas.access.policy.model;

public interface BaseIaasPolicy<T extends BaseIaasPolicy<T>> {
  String ATTR_NAME = "name";
  String ATTR_DOCUMENT = "document";
  String ATTR_DESCRIPTION = "description";
  String ATTR_IAAS_ID = "iaasId";
  String ATTR_IAAS_RESOURCE_NAME = "iaasResourceName";

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
