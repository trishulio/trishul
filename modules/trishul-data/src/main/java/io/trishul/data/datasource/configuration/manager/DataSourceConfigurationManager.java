package io.trishul.data.datasource.configuration.manager;

import java.util.UUID;

public class DataSourceConfigurationManager {
    public String getFqName(String schemaPrefix, UUID tenantId) {
        String tenantName = tenantId.toString().replace("-", "_");
        return String.format("%s%s", schemaPrefix, tenantName);
    }
}
