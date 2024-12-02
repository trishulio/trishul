package io.trishul.user.role.model;

import io.trishul.model.base.dto.BaseDto;

public class AddUserRoleDto extends BaseDto {
    private String name;

    public AddUserRoleDto() {
    }

    public AddUserRoleDto(String name) {
        this();
        setName(name);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
