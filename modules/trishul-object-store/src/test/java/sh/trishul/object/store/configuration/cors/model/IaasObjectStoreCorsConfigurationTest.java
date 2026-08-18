package sh.trishul.object.store.configuration.cors.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

import com.amazonaws.services.s3.model.BucketCrossOriginConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.internal.matchers.apachecommons.ReflectionEquals;

class IaasObjectStoreCorsConfigurationTest {
  private IaasObjectStoreCorsConfiguration config;

  @BeforeEach
  void init() {
    config = new IaasObjectStoreCorsConfiguration();
  }

  @Test
  void testNoArgConstructor() {
    assertNull(config.getBucketName());
    assertNull(config.getBucketCrossOriginConfiguration());
    assertNull(config.getId());
    assertNull(config.getVersion());
  }

  @Test
  void testAllArgConstructor() {
    config = new IaasObjectStoreCorsConfiguration("BUCKET_1", new BucketCrossOriginConfiguration());

    assertEquals("BUCKET_1", config.getBucketName());
    assertTrue(new ReflectionEquals(new BucketCrossOriginConfiguration())
        .matches(config.getBucketCrossOriginConfiguration()));
  }

  @Test
  void testGetSetId() {
    config.setId("BUCKET_1");
    assertEquals("BUCKET_1", config.getId());
  }

  @Test
  void testGetSetBucketCrossOriginConfiguration() {
    config.setBucketCrossOriginConfiguration(new BucketCrossOriginConfiguration());

    assertTrue(new ReflectionEquals(new BucketCrossOriginConfiguration())
        .matches(config.getBucketCrossOriginConfiguration()));
  }

  @Test
  void testGetVersion() {
    assertNull(config.getVersion());
  }

  @Test
  void testAccessId() throws Exception {
    IaasObjectStoreCorsConfiguration accessor = new IaasObjectStoreCorsConfiguration();
    assertSame(accessor, accessor.setId("testString"));
    assertEquals("testString", accessor.getId());
  }

  @Test
  void testAccessBucketName() throws Exception {
    IaasObjectStoreCorsConfiguration accessor = new IaasObjectStoreCorsConfiguration();
    assertSame(accessor, accessor.setBucketName("testString"));
    assertEquals("testString", accessor.getBucketName());
  }

  @Test
  void testAccessBucketCrossOriginConfiguration() throws Exception {
    IaasObjectStoreCorsConfiguration accessor = new IaasObjectStoreCorsConfiguration();
    BucketCrossOriginConfiguration value = mock(BucketCrossOriginConfiguration.class);
    assertSame(accessor, accessor.setBucketCrossOriginConfiguration(value));
    assertEquals(value, accessor.getBucketCrossOriginConfiguration());
  }

}
