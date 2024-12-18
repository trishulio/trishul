package io.trishul.tenant.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.UUID;

@JsonIgnoreProperties({"hibernateLazyInitializer"})
public class AdminTenant extends Tenant {
    public AdminTenant(UUID id, String name) {
        super();
        super.setId(id);
        super.setName(name);
        super.setIsReady(true);
    }

    @Override
    public void setId(UUID id) {
        throwImmutableException();
    }

    @Override
    public void setName(String name) {
        throwImmutableException();
    }

    @Override
    public void setUrl(URL url) {
        throwImmutableException();
    }

    @Override
    public void setCreatedAt(LocalDateTime createdAt) {
        throwImmutableException();
    }

    @Override
    public void setLastUpdated(LocalDateTime lastUpdated) {
        throwImmutableException();
    }

    private void throwImmutableException() {
        throw new UnsupportedOperationException("AdminTenant is immutable. Cannot be updated");
    }
}
