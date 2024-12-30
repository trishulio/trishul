package io.trishul.data.datasource.configuration.builder;

import java.util.Properties;

public abstract class AbstractDataSourceBuilder implements DataSourceBuilder {
  protected Properties props;

  private final String keyUsername;
  private final String keyPassword;
  private final String keyUrl;
  private final String keyAutoCommit;
  private final String keySchema;
  private final String poolSize;

  public AbstractDataSourceBuilder(String keyUsername, String keyPassword, String keyUrl,
      String keyAutoCommit, String keySchema, String poolSize) {
    this.props = new Properties(5);

    this.keyUsername = keyUsername;
    this.keyPassword = keyPassword;
    this.keyUrl = keyUrl;
    this.keyAutoCommit = keyAutoCommit;
    this.keySchema = keySchema;
    this.poolSize = poolSize;
  }

  @Override
  public DataSourceBuilder username(String username) {
    this.props.setProperty(keyUsername, username);
    return this;
  }

  @Override
  public DataSourceBuilder password(String password) {
    props.setProperty(keyPassword, password);
    return this;
  }

  @Override
  public DataSourceBuilder url(String url) {
    props.setProperty(keyUrl, url);
    return this;
  }

  @Override
  public DataSourceBuilder schema(String schema) {
    props.setProperty(keySchema, schema);
    return this;
  }

  @Override
  public DataSourceBuilder poolSize(int size) {
    props.put(poolSize, size);
    return this;
  }

  @Override
  public DataSourceBuilder autoCommit(boolean autoCommit) {
    props.put(keyAutoCommit, autoCommit);
    return this;
  }

  @Override
  public String username() {
    return props.getProperty(keyUsername);
  }

  @Override
  public String password() {
    return props.getProperty(keyPassword);
  }

  @Override
  public String url() {
    return props.getProperty(keyUrl);
  }

  @Override
  public String schema() {
    return props.getProperty(keySchema);
  }

  @Override
  public int poolSize() {
    int size = -1;
    Object o = props.get(poolSize);
    if (o != null) {
      size = (int) o;
    }

    return size;
  }

  @Override
  public boolean autoCommit() {
    boolean b = false;
    Object o = props.get(keyAutoCommit);
    if (o != null) {
      b = (Boolean) o;
    }

    return b;
  }

  @Override
  public DataSourceBuilder clear() {
    this.props.clear();
    return this;
  }
}
