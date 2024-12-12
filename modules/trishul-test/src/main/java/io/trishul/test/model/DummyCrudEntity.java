package io.trishul.test.model;

import org.springframework.context.annotation.Profile;

import io.trishul.model.base.pojo.CrudEntity;

@Profile("IgnoredFromSpringContextTests")
public class DummyCrudEntity extends BaseEntity implements CrudEntity<Long>, BaseDummyCrudEntity, UpdateDummyCrudEntity {
    private Long id;
    private String value;
    private String excludedValue;
    private Integer version;

    public DummyCrudEntity() {
    }

    public DummyCrudEntity(Long id, String value, String excludedValue, Integer version) {
        this.setId(id);
        this.setValue(value);
        this.setExcludedValue(excludedValue);
        this.setVersion(version);
    }

    public DummyCrudEntity(Long id) {
        this.setId(id);
    }

    @Override
    public Long getId() {
        return this.id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String getValue() {
        return this.value;
    }

    @Override
    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public Integer getVersion() {
        return this.version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    @Override
    public String getExcludedValue() {
        return this.excludedValue;
    }

    @Override
    public void setExcludedValue(String excludedValue) {
        this.excludedValue = excludedValue;
    }
}