package io.trishul.secrets;

import java.io.IOException;

public interface SecretsManager<K, V> {
    public V get(K secretId) throws IOException ;

    public void put(K secretId, V secret) throws IOException;

    public void create(K secretId, V secret) throws IOException;

    public void update(K secretId, V secret) throws IOException;

    public Boolean exists(K secretId) throws IOException;

    public boolean remove(K secretId) throws IOException;
}
