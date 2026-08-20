package sh.trishul.integration.model;

public interface IntegrationAccessor<T extends IntegrationAccessor<T>> {
  String ATTR_INTEGRATION = "integration";

  Integration getIntegration();

  T setIntegration(Integration integration);
}
