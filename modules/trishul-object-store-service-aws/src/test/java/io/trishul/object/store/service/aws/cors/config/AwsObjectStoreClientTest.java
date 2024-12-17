package io.trishul.object.store.service.aws.cors.config;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.CreateBucketRequest;
import com.amazonaws.services.s3.model.DeleteBucketRequest;

import io.trishul.object.store.aws.model.mapper.AwsIaasObjectStoreMapper;
import io.trishul.object.store.model.IaasObjectStore;

public class AwsObjectStoreClientTest {
    private AwsObjectStoreClient client;

    private AmazonS3 s3;

    @BeforeEach
    public void init() {
        s3 = mock(AmazonS3.class);
        client = new AwsObjectStoreClient(s3, AwsIaasObjectStoreMapper.INSTANCE);
    }

    @Test
    public void testGet_ReturnsBucketWithName() {
        doReturn(List.of(new Bucket("B1"), new Bucket("B2"))).when(s3).listBuckets(any());

        IaasObjectStore objectStore = client.get("B1");

        IaasObjectStore expected = new IaasObjectStore("B1");
        assertEquals(expected, objectStore);
    }

    @Test
    public void testDelete_ReturnsTrue_WhenEntityExists() {
        doAnswer(inv -> {
            assertEquals("B1", inv.getArgument(0, DeleteBucketRequest.class).getBucketName());
            return null;
        }).when(s3).deleteBucket(any(DeleteBucketRequest.class));

        assertTrue(client.delete("B1"));

        verify(s3, times(1)).deleteBucket(any(DeleteBucketRequest.class));
    }

    @Test
    @Disabled("This test is disabled because of TODO: manually validate the AmazonS3Exception is only thrown when bucket is failed to be delete because it's not there. If that's true, enable this test.")
    public void testDelete_ReturnsFalse_WhenEntityDoesNotExists() {
        doAnswer(inv -> {
            assertEquals("B1", inv.getArgument(0, DeleteBucketRequest.class).getBucketName());
            throw new AmazonS3Exception("Cannot delete B1");
        }).when(s3).deleteBucket(any(DeleteBucketRequest.class));

        assertFalse(client.delete("B1"));

        verify(s3, times(1)).deleteBucket(any(DeleteBucketRequest.class));
    }

    @Test
    public void testAdd_ReturnsCreatedBucket() {
        doAnswer(inv -> {
            CreateBucketRequest req = inv.getArgument(0, CreateBucketRequest.class);
            return new Bucket(req.getBucketName());
        }).when(s3).createBucket(any(CreateBucketRequest.class));

        IaasObjectStore objectStore = client.add(new IaasObjectStore("B1"));

        IaasObjectStore expected = new IaasObjectStore("B1");
        assertEquals(expected, objectStore);
    }

    @Test
    public void testExists_ReturnsTrue_WhenS3ReturnsTrue() {
        doReturn(true).when(s3).doesBucketExistV2("B1");

        assertTrue(client.exists("B1"));
    }

    @Test
    public void testExists_ReturnsFalse_WhenS3ReturnsFalse() {
        doReturn(false).when(s3).doesBucketExistV2("B1");

        assertFalse(client.exists("B1"));
    }

    @Test
    public void testPut_CallsAdd_WhenExistIsFalse() {
        doReturn(false).when(s3).doesBucketExistV2("B1");

        doAnswer(inv -> {
            CreateBucketRequest req = inv.getArgument(0, CreateBucketRequest.class);
            return new Bucket(req.getBucketName());
        }).when(s3).createBucket(any(CreateBucketRequest.class));

        IaasObjectStore objectStore = client.put(new IaasObjectStore("B1"));

        IaasObjectStore expected = new IaasObjectStore("B1");
        assertEquals(expected, objectStore);
    }

    @Test
    public void testPut_CallsGet_WhenExistIsTrue() {
        doReturn(true).when(s3).doesBucketExistV2("B1");

        doReturn(List.of(new Bucket("B1"), new Bucket("B2"))).when(s3).listBuckets(any());

        IaasObjectStore objectStore = client.put(new IaasObjectStore("B1"));

        IaasObjectStore expected = new IaasObjectStore("B1");
        assertEquals(expected, objectStore);

    }

    @Test
    public void testCaching_SingleThreaded_CacheIsCleanedWhenMutationOperationIsPerformed() {
        doReturn(List.of(new Bucket("B1"), new Bucket("B2"), new Bucket("B3"))).when(s3).listBuckets(any());

        assertEquals(new IaasObjectStore("B1"), client.get("B1"));
        assertEquals(new IaasObjectStore("B2"), client.get("B2"));
        assertEquals(new IaasObjectStore("B3"), client.get("B3"));
        verify(s3, times(1)).listBuckets(any());

        // Testing that add operation resets cache
        doAnswer(inv -> {
            CreateBucketRequest req = inv.getArgument(0, CreateBucketRequest.class);
            return new Bucket(req.getBucketName());
        }).when(s3).createBucket(any(CreateBucketRequest.class));
        client.add(new IaasObjectStore("B1"));

        assertEquals(new IaasObjectStore("B1"), client.get("B1"));
        verify(s3, times(2)).listBuckets(any());

        // Testing that delete operation resets cache.
        doAnswer(inv -> {
            assertEquals("B1", inv.getArgument(0, DeleteBucketRequest.class).getBucketName());
            return null;
        }).when(s3).deleteBucket(any(DeleteBucketRequest.class));
        client.delete("B1");

        assertEquals(new IaasObjectStore("B1"), client.get("B1"));
        verify(s3, times(3)).listBuckets(any());

    }
}