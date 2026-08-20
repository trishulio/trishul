package sh.trishul.integration.model;

public interface BaseIntegration<T extends BaseIntegration<T>> {
  String ATTR_NAME = "name";
  String ATTR_TYPE = "type";
  String ATTR_PROVIDER = "provider";
  String ATTR_STATUS = "status";
  String ATTR_CONFIGURATION = "configuration";

  String getName();

  T setName(String name);

  IntegrationType getType();

  T setType(IntegrationType type);

  String getProvider();

  T setProvider(String provider);

  IntegrationStatus getStatus();

  T setStatus(IntegrationStatus status);

  String getConfiguration();

  T setConfiguration(String configuration);
}
