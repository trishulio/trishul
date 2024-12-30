package io.trishul.object.store.configuration.cors.model;

public interface IaasObjectStoreCorsConfigurationAccessor<T extends IaasObjectStoreCorsConfigurationAccessor<T>> {

  IaasObjectStoreCorsConfiguration getIaasObjectStoreCorsConfiguration();

  T setIaasObjectStoreCorsConfiguration(IaasObjectStoreCorsConfiguration file);
}
