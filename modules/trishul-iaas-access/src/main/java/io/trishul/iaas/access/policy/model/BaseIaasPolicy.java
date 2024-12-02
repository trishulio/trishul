package io.trishul.iaas.access.policy.model;

public interface BaseIaasPolicy {
    final String ATTR_NAME = "name";
    final String ATTR_DOCUMENT = "document";
    final String ATTR_DESCRIPTION = "description";
    final String ATTR_IAAS_ID = "iaasId";
    final String ATTR_IAAS_RESOURCE_NAME = "iaasResourceName";

    String getName();

    void setName(String name);

    String getDocument();

    void setDocument(String document);

    String getDescription();

    void setDescription(String description);

    String getIaasId();

    void setIaasId(String iaasId);

    String getIaasResourceName();

    void setIaasResourceName(String iaasResourceName);
}
