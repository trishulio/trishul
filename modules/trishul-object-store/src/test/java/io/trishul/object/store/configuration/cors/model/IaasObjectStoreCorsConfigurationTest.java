package io.company.brewcraft.model;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.internal.matchers.apachecommons.ReflectionEquals;

import com.amazonaws.services.s3.model.BucketCrossOriginConfiguration;

public class IaasObjectStoreCorsConfigurationTest {
    private IaasObjectStoreCorsConfiguration config;

    @BeforeEach
    public void init() {
        config = new IaasObjectStoreCorsConfiguration();
    }

    @Test
    public void testNoArgConstructor() {
        assertNull(config.getBucketName());
        assertNull(config.getBucketCrossOriginConfiguration());
        assertNull(config.getId());
        assertNull(config.getVersion());
    }

    @Test
    public void testAllArgConstructor() {
        config = new IaasObjectStoreCorsConfiguration("BUCKET_1", new BucketCrossOriginConfiguration());

        assertEquals("BUCKET_1", config.getBucketName());
        assertTrue(new ReflectionEquals(new BucketCrossOriginConfiguration()).matches(config.getBucketCrossOriginConfiguration()));
    }

    @Test
    public void testGetSetId() {
        config.setId("BUCKET_1");
        assertEquals("BUCKET_1", config.getId());
    }

    @Test
    public void testGetSetBucketCrossOriginConfiguration() {
        config.setBucketCrossOriginConfiguration(new BucketCrossOriginConfiguration());

        assertTrue(new ReflectionEquals(new BucketCrossOriginConfiguration()).matches(config.getBucketCrossOriginConfiguration()));
    }

    @Test
    public void testGetVersion() {
        assertNull(config.getVersion());
    }
}
