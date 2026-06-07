package io.trishul.ai.chat.model;

public interface BaseAiChatModelConfig<T extends BaseAiChatModelConfig<T>> {
  final String ATTR_NAME = "name";
  final String ATTR_PROVIDER = "provider";
  final String ATTR_MODEL_NAME = "modelName";
  final String ATTR_STREAMING_MODEL_NAME = "streamingModelName";
  final String ATTR_API_KEY = "apiKey";
  final String ATTR_TEMPERATURE = "temperature";
  final String ATTR_MAX_TOKENS = "maxTokens";
  final String ATTR_TOP_P = "topP";
  final String ATTR_IS_DEFAULT = "isDefault";

  String getName();

  T setName(String name);

  String getProvider();

  T setProvider(String provider);

  String getModelName();

  T setModelName(String modelName);

  String getStreamingModelName();

  T setStreamingModelName(String streamingModelName);

  String getApiKey();

  T setApiKey(String apiKey);

  Double getTemperature();

  T setTemperature(Double temperature);

  Integer getMaxTokens();

  T setMaxTokens(Integer maxTokens);

  Double getTopP();

  T setTopP(Double topP);

  Boolean getIsDefault();

  T setIsDefault(Boolean isDefault);
}
