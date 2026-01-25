package io.trishul.object.store.configuration.access.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
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
  void testGetVersion() {
    assertNull(iaasObjectStoreAccessConfig.getVersion());
  }
}
