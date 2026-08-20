package sh.trishul.ai.chat.model;

public interface BaseAiChatModelConfig<T extends BaseAiChatModelConfig<T>> {
  String ATTR_NAME = "name";
  String ATTR_PROVIDER = "provider";
  String ATTR_MODEL_NAME = "modelName";
  String ATTR_STREAMING_MODEL_NAME = "streamingModelName";
  String ATTR_API_KEY = "apiKey";
  String ATTR_TEMPERATURE = "temperature";
  String ATTR_MAX_TOKENS = "maxTokens";
  String ATTR_TOP_P = "topP";
  String ATTR_IS_DEFAULT = "isDefault";

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
