package io.trishul.tenant.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.UUID;

@JsonIgnoreProperties({"hibernateLazyInitializer"})
public class AdminTenant extends Tenant {
    public AdminTenant(UUID id, String name) {
        super();
        super.setId(id);
        super.setName(name);
        super.setIsReady(true);
    }
    // TODO: Decouple admin tenant from Tenant since admin tenant is immutable

    // @Override
    // public final void setId(UUID id) {
    // throwImmutableException();
    // }

    // @Override
    // public final void setName(String name) {
    // throwImmutableException();
    // }

    // @Override
    // public final void setUrl(URL url) {
    // throwImmutableException();
    // }

    // @Override
    // public final void setCreatedAt(LocalDateTime createdAt) {
    // throwImmutableException();
    // }

    // @Override
    // public final void setLastUpdated(LocalDateTime lastUpdated) {
    // throwImmutableException();
    // }

    // private void throwImmutableException() {
    // throw new UnsupportedOperationException("AdminTenant is immutable. Cannot be
    // updated");
    // }
}
