package sh.trishul.secrets;

import java.io.IOException;

public interface SecretsManager<K, V> {
  V get(K secretId) throws IOException;

  void put(K secretId, V secret) throws IOException;

  void create(K secretId, V secret) throws IOException;

  void update(K secretId, V secret) throws IOException;

  Boolean exists(K secretId) throws IOException;

  boolean remove(K secretId) throws IOException;
}
