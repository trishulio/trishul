package io.trishul.iaas.idp.tenant.model;

import io.trishul.base.types.base.pojo.Audited;
import io.trishul.base.types.base.pojo.CrudEntity;
import io.trishul.iaas.access.role.model.IaasRole;
import io.trishul.model.base.pojo.BaseModel;
import java.time.LocalDateTime;

// TODO: Rename all the tenant stuff in the IdpTenant as IdpGroup
public class IaasIdpTenant extends BaseModel
        implements UpdateIaasIdpTenant, CrudEntity<String>, Audited {
    private String name;
    private IaasRole role;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime lastUpdated;

    public IaasIdpTenant() {
        super();
    }

    public IaasIdpTenant(String name) {
        this();
        setName(name);
    }

    public IaasIdpTenant(
            String name,
            IaasRole role,
            String description,
            LocalDateTime createdAt,
            LocalDateTime lastUpdated) {
        this(name);
        setIaasRole(role);
        setDescription(description);
        setCreatedAt(createdAt);
        setLastUpdated(lastUpdated);
    }

    @Override
    public String getId() {
        return getName();
    }

    @Override
    public void setId(String id) {
        setName(id);
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getDescription() {
        return this.description;
    }

    @Override
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public IaasRole getIaasRole() {
        return this.role;
    }

    @Override
    public void setIaasRole(IaasRole role) {
        this.role = role;
    }

    @Override
    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }

    @Override
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public LocalDateTime getLastUpdated() {
        return this.lastUpdated;
    }

    @Override
    public void setLastUpdated(LocalDateTime lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    @Override
    public Integer getVersion() {
        // Versioning not implemented due to lack of use-case
        return null;
    }
}
