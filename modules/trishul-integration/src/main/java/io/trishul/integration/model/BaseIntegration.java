package io.trishul.integration.model;

public interface BaseIntegration<T extends BaseIntegration<T>> {
  final String ATTR_NAME = "name";
  final String ATTR_TYPE = "type";
  final String ATTR_PROVIDER = "provider";
  final String ATTR_STATUS = "status";
  final String ATTR_CONFIGURATION = "configuration";

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
