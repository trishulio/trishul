package io.trishul.tenant.dto;

import io.trishul.model.base.dto.BaseDto;
import jakarta.validation.constraints.NotBlank;
import java.net.URL;
import java.util.UUID;

public class UpdateTenantDto extends BaseDto {
    private UUID id;

    @NotBlank private String name;

    @NotBlank private URL url;

    public UpdateTenantDto() {
        super();
    }

    public UpdateTenantDto(UUID id) {
        this();
        setId(id);
    }

    public UpdateTenantDto(UUID id, String name, URL url) {
        this(id);
        setName(name);
        setUrl(url);
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
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
