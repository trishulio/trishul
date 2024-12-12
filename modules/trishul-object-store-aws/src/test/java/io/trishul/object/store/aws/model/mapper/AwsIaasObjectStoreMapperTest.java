package io.company.brewcraft.service;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;
import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.amazonaws.services.s3.model.Bucket;

import io.company.brewcraft.model.IaasObjectStore;

public class AwsIaasObjectStoreMapperTest {
    private AwsIaasObjectStoreMapper mapper;

    @BeforeEach
    public void init() {
        mapper = AwsIaasObjectStoreMapper.INSTANCE;
    }

    @Test
    public void testFromIaasEntity_ReturnsNull_WhenArgIsNull() {
        assertNull(mapper.fromIaasEntity(null));
    }

    @Test
    public void testFromIaasEntity_ReturnsEntity_WhenArgIsNotNull() {
        Bucket bucket = new Bucket("B1");
        bucket.setCreationDate(new Date(1, 1, 1));

        IaasObjectStore objectStore = mapper.fromIaasEntity(bucket);

        IaasObjectStore expected = new IaasObjectStore("B1", LocalDateTime.of(1901, 2, 1, 0, 0), null);
        assertEquals(expected, objectStore);
    }
}
