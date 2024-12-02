package io.trishul.iaas.access.role.model;

import java.time.LocalDateTime;

public interface BaseIaasRole {
    final String ATTR_NAME = "name";
    final String ATTR_DESCRIPTION = "description";
    final String ATTR_ASSUME_POLICY_DOCUMENT = "assumePolicyDocument";
    final String ATTR_IAAS_RESOURCE_NAME = "iaasResourceName";
    final String ATTR_IAAS_ID = "iaasId";
    final String ATTR_LAST_USED = "lastUsed";

    String getName();

    void setName(String name);

    String getDescription();

    void setDescription(String description);

    String getAssumePolicyDocument();

    void setAssumePolicyDocument(String assumePolicyDocument);

    String getIaasResourceName();

    void setIaasResourceName(String iaasResourceName);

    String getIaasId();

    void setIaasId(String iaasId);

    LocalDateTime getLastUsed();

    void setLastUsed(LocalDateTime lastUsed);
}
