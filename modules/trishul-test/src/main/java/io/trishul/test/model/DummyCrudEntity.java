package io.trishul.test.model;

import io.trishul.base.types.base.pojo.CrudEntity;
import io.trishul.model.base.entity.BaseEntity;

public class DummyCrudEntity extends BaseEntity implements CrudEntity<Long>, UpdateDummyCrudEntity {
    private Long id;
    private String value;
    private String excludedValue;
    private Integer version;

    public DummyCrudEntity() {}

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
    public final void setId(Long id) {
        this.id = id;
    }

    @Override
    public String getValue() {
        return this.value;
    }

    @Override
    public final void setValue(String value) {
        this.value = value;
    }

    @Override
    public Integer getVersion() {
        return this.version;
    }

    public final void setVersion(Integer version) {
        this.version = version;
    }

    @Override
    public String getExcludedValue() {
        return this.excludedValue;
    }

    @Override
    public final void setExcludedValue(String excludedValue) {
        this.excludedValue = excludedValue;
    }
}
