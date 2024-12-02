package io.trishul.user.role.model;

import io.trishul.model.base.dto.BaseDto;

public class UpdateUserRoleDto extends BaseDto {
    private Long id;
    private String name;
    private Integer version;

    public UpdateUserRoleDto() {
        super();
    }

    public UpdateUserRoleDto(Long id) {
        this();
        setId(id);
    }

    public UpdateUserRoleDto(Long id, String name, Integer version) {
        this(id);
        setName(name);
        setVersion(version);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
}
