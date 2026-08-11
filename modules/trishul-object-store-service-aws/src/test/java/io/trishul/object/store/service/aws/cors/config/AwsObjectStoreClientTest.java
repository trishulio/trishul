package io.trishul.object.store.service.aws.cors.config;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.CreateBucketRequest;
import com.amazonaws.services.s3.model.DeleteBucketRequest;
import com.amazonaws.services.s3.model.ListBucketsRequest;
import io.trishul.object.store.aws.model.mapper.AwsIaasObjectStoreMapper;
import io.trishul.object.store.model.IaasObjectStore;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AwsObjectStoreClientTest {
  private AwsObjectStoreClient client;

  private AmazonS3 s3;

  @BeforeEach
  void init() {
    s3 = mock(AmazonS3.class);
    client = new AwsObjectStoreClient(s3, AwsIaasObjectStoreMapper.INSTANCE);
  }

  @Test
  void testGet_ReturnsBucketWithName() {
    doReturn(List.of(new Bucket("B1"), new Bucket("B2"))).when(s3)
        .listBuckets(any(ListBucketsRequest.class));

    IaasObjectStore objectStore = client.get("B1");

    IaasObjectStore expected = new IaasObjectStore("B1");
    assertEquals(expected, objectStore);
  }

  @Test
  void testDelete_ReturnsTrue_WhenEntityExists() {
    doAnswer(inv -> {
      assertEquals("B1", inv.getArgument(0, DeleteBucketRequest.class).getBucketName());
      return null;
    }).when(s3).deleteBucket(any(DeleteBucketRequest.class));

    assertTrue(client.delete("B1"));

    verify(s3, times(1)).deleteBucket(any(DeleteBucketRequest.class));
  }

  @Test
  void testDelete_ReturnsFalse_WhenEntityDoesNotExists() {
    doAnswer(inv -> {
      assertEquals("B1", inv.getArgument(0, DeleteBucketRequest.class).getBucketName());
      AmazonS3Exception ex = new AmazonS3Exception("Cannot delete B1");
      ex.setStatusCode(500);
      throw ex;
    }).when(s3).deleteBucket(any(DeleteBucketRequest.class));

    assertFalse(client.delete("B1"));

    verify(s3, times(1)).deleteBucket(any(DeleteBucketRequest.class));
  }

  @Test
  void testDelete_ReturnsTrue_WhenEntityDoesNotExistAndS3Throws404NoSuchBucket() {
    doAnswer(inv -> {
      assertEquals("B1", inv.getArgument(0, DeleteBucketRequest.class).getBucketName());
      AmazonS3Exception ex = new AmazonS3Exception("NoSuchBucket");
      ex.setStatusCode(404);
      ex.setErrorCode("NoSuchBucket");
      throw ex;
    }).when(s3).deleteBucket(any(DeleteBucketRequest.class));

    assertTrue(client.delete("B1"));

    verify(s3, times(1)).deleteBucket(any(DeleteBucketRequest.class));
  }

  @Test
  void testGet_ReturnsNull_WhenBucketDoesNotExist() {
    doReturn(List.of(new Bucket("B2"))).when(s3).listBuckets(any(ListBucketsRequest.class));

    IaasObjectStore objectStore = client.get("B1");

    assertEquals(null, objectStore);
  }

  @Test
  void testGet_ThrowsNullPointerException_WhenBucketsAreNull() {
    doReturn(null).when(s3).listBuckets(any(ListBucketsRequest.class));

    assertThrows(NullPointerException.class, () -> client.get("B1"));
  }

  @Test
  void testObjectStores_CachesBuckets() {
    doReturn(List.of(new Bucket("B1"))).when(s3).listBuckets(any(ListBucketsRequest.class));

    client.get("B1");
    client.get("B1");

    verify(s3, times(1)).listBuckets(any(ListBucketsRequest.class));
  }

  @Test
  void testAdd_ReturnsCreatedBucket() {
    doAnswer(inv -> {
      CreateBucketRequest req = inv.getArgument(0, CreateBucketRequest.class);
      return new Bucket(req.getBucketName());
    }).when(s3).createBucket(any(CreateBucketRequest.class));

    IaasObjectStore objectStore = client.add(new IaasObjectStore("B1"));

    IaasObjectStore expected = new IaasObjectStore("B1");
    assertEquals(expected, objectStore);
  }

  @Test
  void testExists_ReturnsTrue_WhenS3ReturnsTrue() {
    doReturn(true).when(s3).doesBucketExistV2("B1");

    assertTrue(client.exists("B1"));
  }

  @Test
  void testExists_ReturnsFalse_WhenS3ReturnsFalse() {
    doReturn(false).when(s3).doesBucketExistV2("B1");

    assertFalse(client.exists("B1"));
  }

  @Test
  void testPut_CallsAdd_WhenExistIsFalse() {
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
  void testPut_CallsGet_WhenExistIsTrue() {
    doReturn(true).when(s3).doesBucketExistV2("B1");

    doReturn(List.of(new Bucket("B1"), new Bucket("B2"))).when(s3)
        .listBuckets(any(ListBucketsRequest.class));

    IaasObjectStore objectStore = client.put(new IaasObjectStore("B1"));

    IaasObjectStore expected = new IaasObjectStore("B1");
    assertEquals(expected, objectStore);
  }

  @Test
  void testCaching_SingleThreaded_CacheIsCleanedWhenMutationOperationIsPerformed() {
    doReturn(List.of(new Bucket("B1"), new Bucket("B2"), new Bucket("B3"))).when(s3)
        .listBuckets(any(ListBucketsRequest.class));

    assertEquals(new IaasObjectStore("B1"), client.get("B1"));
    assertEquals(new IaasObjectStore("B2"), client.get("B2"));
    assertEquals(new IaasObjectStore("B3"), client.get("B3"));
    verify(s3, times(1)).listBuckets(any(ListBucketsRequest.class));

    // Testing that add operation resets cache
    doAnswer(inv -> {
      CreateBucketRequest req = inv.getArgument(0, CreateBucketRequest.class);
      return new Bucket(req.getBucketName());
    }).when(s3).createBucket(any(CreateBucketRequest.class));
    client.add(new IaasObjectStore("B1"));

    assertEquals(new IaasObjectStore("B1"), client.get("B1"));
    verify(s3, times(2)).listBuckets(any(ListBucketsRequest.class));

    // Testing that delete operation resets cache.
    doAnswer(inv -> {
      assertEquals("B1", inv.getArgument(0, DeleteBucketRequest.class).getBucketName());
      return null;
    }).when(s3).deleteBucket(any(DeleteBucketRequest.class));
    client.delete("B1");

    assertEquals(new IaasObjectStore("B1"), client.get("B1"));
    verify(s3, times(3)).listBuckets(any(ListBucketsRequest.class));
  }
}
