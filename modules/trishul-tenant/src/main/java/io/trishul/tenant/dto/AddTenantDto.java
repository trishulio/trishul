package io.trishul.tenant.dto;

import io.trishul.model.base.dto.BaseDto;
import jakarta.validation.constraints.NotBlank;
import java.net.URL;

public class AddTenantDto extends BaseDto {
    @NotBlank private String name;

    @NotBlank private URL url;

    public AddTenantDto() {
        super();
    }

    public AddTenantDto(String name, URL url) {
        setName(name);
        setUrl(url);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public URL getUrl() {
        return url;
    }

    public void setUrl(URL url) {
        this.url = url;
    }
}
