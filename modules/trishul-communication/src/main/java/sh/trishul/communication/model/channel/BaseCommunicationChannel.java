package sh.trishul.communication.model.channel;

public interface BaseCommunicationChannel<T extends BaseCommunicationChannel<T>> {
  final String ATTR_ADDRESS = "address";
  final String ATTR_CHANNEL_TYPE = "channelType";
  final String ATTR_DISPLAY_NAME = "displayName";
  final String ATTR_CAPABILITIES = "capabilities";

  String getAddress();

  T setAddress(String address);

  ChannelType getChannelType();

  T setChannelType(ChannelType channelType);

  String getDisplayName();

  T setDisplayName(String displayName);

  String getCapabilities();

  T setCapabilities(String capabilities);
}
