package sh.trishul.integration.communication.model;

public interface IntegrationCommunicationConfigAccessor<T extends IntegrationCommunicationConfigAccessor<T>> {
  String ATTR_INTEGRATION_COMMUNICATION_CONFIG = "integrationCommunicationConfig";

  IntegrationCommunicationConfig getIntegrationCommunicationConfig();

  T setIntegrationCommunicationConfig(IntegrationCommunicationConfig config);
}
