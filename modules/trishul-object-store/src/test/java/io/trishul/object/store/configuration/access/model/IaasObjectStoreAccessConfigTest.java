package io.trishul.object.store.configuration.access.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.amazonaws.services.s3.model.PublicAccessBlockConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.internal.matchers.apachecommons.ReflectionEquals;

class IaasObjectStoreAccessConfigTest {
  private IaasObjectStoreAccessConfig iaasObjectStoreAccessConfig;

  @BeforeEach
  void init() {
    iaasObjectStoreAccessConfig = new IaasObjectStoreAccessConfig();
  }

  @Test
  void testNoArgConstructor() {
    assertNull(iaasObjectStoreAccessConfig.getObjectStoreName());
    assertNull(iaasObjectStoreAccessConfig.getPublicAccessBlockConfig());
    assertNull(iaasObjectStoreAccessConfig.getId());
    assertNull(iaasObjectStoreAccessConfig.getVersion());
  }

  @Test
  void testAllArgConstructor() {
    iaasObjectStoreAccessConfig
        = new IaasObjectStoreAccessConfig("BUCKET_1", new PublicAccessBlockConfiguration());

    assertEquals("BUCKET_1", iaasObjectStoreAccessConfig.getObjectStoreName());
    assertTrue(new ReflectionEquals(new PublicAccessBlockConfiguration())
        .matches(iaasObjectStoreAccessConfig.getPublicAccessBlockConfig()));
  }

  @Test
  void testGetSetId() {
    iaasObjectStoreAccessConfig.setId("BUCKET_1");
    assertEquals("BUCKET_1", iaasObjectStoreAccessConfig.getId());
  }

  @Test
  void testGetSetBucketCrossOriginConfiguration() {
    iaasObjectStoreAccessConfig.setPublicAccessBlockConfig(new PublicAccessBlockConfiguration());

    assertTrue(new ReflectionEquals(new PublicAccessBlockConfiguration())
        .matches(iaasObjectStoreAccessConfig.getPublicAccessBlockConfig()));
  }

  @Test
  void testIdConstructor() {
    iaasObjectStoreAccessConfig = new IaasObjectStoreAccessConfig("BUCKET_1");
    assertEquals("BUCKET_1", iaasObjectStoreAccessConfig.getId());
  }

  @Test
  void testConstructor_NullConfig() {
    iaasObjectStoreAccessConfig = new IaasObjectStoreAccessConfig("BUCKET_1", null);
    assertNull(iaasObjectStoreAccessConfig.getPublicAccessBlockConfig());
  }

  @Test
  void testSetPublicAccessBlockConfig_Null() {
    iaasObjectStoreAccessConfig.setPublicAccessBlockConfig(null);
    assertNull(iaasObjectStoreAccessConfig.getPublicAccessBlockConfig());
  }

  @Test
  void testGetVersion() {
    assertNull(iaasObjectStoreAccessConfig.getVersion());
  }

  @Test
  void testAccessId() throws Exception {
    IaasObjectStoreAccessConfig accessor = new IaasObjectStoreAccessConfig();
    assertSame(accessor, accessor.setId("testString"));
    assertEquals("testString", accessor.getId());
  }

  @Test
  void testAccessObjectStoreName() throws Exception {
    IaasObjectStoreAccessConfig accessor = new IaasObjectStoreAccessConfig();
    assertSame(accessor, accessor.setObjectStoreName("testString"));
    assertEquals("testString", accessor.getObjectStoreName());
  }

  @Test
  void testAccessPublicAccessBlockConfig() throws Exception {
    IaasObjectStoreAccessConfig accessor = new IaasObjectStoreAccessConfig();
    PublicAccessBlockConfiguration value = mock(PublicAccessBlockConfiguration.class);
    when(value.clone()).thenReturn(value);
    assertSame(accessor, accessor.setPublicAccessBlockConfig(value));
    assertEquals(value, accessor.getPublicAccessBlockConfig());
  }

}
