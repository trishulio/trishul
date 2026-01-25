package io.trishul.object.store.service.aws.cors.config;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.BucketCrossOriginConfiguration;
import com.amazonaws.services.s3.model.DeleteBucketCrossOriginConfigurationRequest;
import com.amazonaws.services.s3.model.GetBucketCrossOriginConfigurationRequest;
import com.amazonaws.services.s3.model.SetBucketCrossOriginConfigurationRequest;
import io.trishul.object.store.configuration.cors.model.IaasObjectStoreCorsConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.internal.matchers.apachecommons.ReflectionEquals;

class AwsCorsConfigClientTest {
  private AwsCorsConfigClient client;

  private AmazonS3 mAwsClient;

  @BeforeEach
  void init() {
    mAwsClient = mock(AmazonS3.class);

    client = new AwsCorsConfigClient(mAwsClient);
  }

  @Test
  void testGet_ReturnsIaasBucketCrossOriginConfiguration() {
    doReturn(new BucketCrossOriginConfiguration()).when(mAwsClient)
        .getBucketCrossOriginConfiguration(any(GetBucketCrossOriginConfigurationRequest.class));

    IaasObjectStoreCorsConfiguration config = client.get("BUCKET_1");

    assertEquals("BUCKET_1", config.getBucketName());
    assertTrue(new ReflectionEquals(new BucketCrossOriginConfiguration())
        .matches(config.getBucketCrossOriginConfiguration()));
  }

  @Test
  void testGet_ReturnsNull_WhenClientThrowsException() {
    doThrow(AmazonS3Exception.class).when(mAwsClient)
        .getBucketCrossOriginConfiguration(any(GetBucketCrossOriginConfigurationRequest.class));

    assertNull(client.get("BUCKET_1"));
  }

  @Test
  void testAdd_ReturnsAddedAttachement() {
    client = spy(client);
    doReturn(new IaasObjectStoreCorsConfiguration("BUCKET_1", new BucketCrossOriginConfiguration()))
        .when(client).get("BUCKET_1");

    IaasObjectStoreCorsConfiguration config = client.add(
        new IaasObjectStoreCorsConfiguration("BUCKET_1", new BucketCrossOriginConfiguration()));

    assertEquals("BUCKET_1", config.getBucketName());
    assertTrue(new ReflectionEquals(new BucketCrossOriginConfiguration())
        .matches(config.getBucketCrossOriginConfiguration()));
    ArgumentCaptor<SetBucketCrossOriginConfigurationRequest> argument
        = ArgumentCaptor.forClass(SetBucketCrossOriginConfigurationRequest.class);
    verify(mAwsClient).setBucketCrossOriginConfiguration(argument.capture());
    assertEquals("BUCKET_1", argument.getValue().getBucketName());
    assertTrue(new ReflectionEquals(new BucketCrossOriginConfiguration())
        .matches(argument.getValue().getCrossOriginConfiguration()));
  }

  @Test
  void testPut_ReturnsEntity_WhenGetReturnsEntity() {
    client = spy(client);
    doReturn(new IaasObjectStoreCorsConfiguration("BUCKET_1", new BucketCrossOriginConfiguration()))
        .when(client).get("BUCKET_1");

    IaasObjectStoreCorsConfiguration config = client.put(
        new IaasObjectStoreCorsConfiguration("BUCKET_1", new BucketCrossOriginConfiguration()));

    assertEquals("BUCKET_1", config.getBucketName());
    assertTrue(new ReflectionEquals(new BucketCrossOriginConfiguration())
        .matches(config.getBucketCrossOriginConfiguration()));
  }

  @Test
  void testExists_ReturnsFalse_WhenGetReturnsNull() {
    client = spy(client);
    doReturn(null).when(client).get("BUCKET_1");

    assertFalse(client.exists("BUCKET_1"));
  }

  @Test
  void testExists_ReturnsTrue_WhenGetReturnsEntity() {
    client = spy(client);
    doReturn(new IaasObjectStoreCorsConfiguration("BUCKET_1", new BucketCrossOriginConfiguration()))
        .when(client).get("BUCKET_1");

    assertTrue(client.exists("BUCKET_1"));
  }

  @Test
  void testDelete_ReturnsTrue_WhenDeleteIsSuccessful() {
    doNothing().when(mAwsClient).deleteBucketCrossOriginConfiguration(
        new DeleteBucketCrossOriginConfigurationRequest("BUCKET_1"));

    assertTrue(client.delete("BUCKET_1"));

    ArgumentCaptor<DeleteBucketCrossOriginConfigurationRequest> argument
        = ArgumentCaptor.forClass(DeleteBucketCrossOriginConfigurationRequest.class);
    verify(mAwsClient, times(1)).deleteBucketCrossOriginConfiguration(argument.capture());
    assertEquals("BUCKET_1", argument.getValue().getBucketName());
  }

  @Test
  void testDelete_ReturnsFalse_WhenEntityDoesNotExist() {
    doThrow(AmazonS3Exception.class).when(mAwsClient).deleteBucketCrossOriginConfiguration(
        any(DeleteBucketCrossOriginConfigurationRequest.class));
    assertFalse(client.delete("BUCKET_1"));
  }
}
