package io.trishul.object.store.service.aws.cors.config;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.DeletePublicAccessBlockRequest;
import com.amazonaws.services.s3.model.GetPublicAccessBlockRequest;
import com.amazonaws.services.s3.model.GetPublicAccessBlockResult;
import com.amazonaws.services.s3.model.PublicAccessBlockConfiguration;
import com.amazonaws.services.s3.model.SetPublicAccessBlockRequest;
import io.trishul.object.store.configuration.access.model.IaasObjectStoreAccessConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.internal.matchers.apachecommons.ReflectionEquals;

public class AwsPublicAccessBlockClientTest {
  private AwsPublicAccessBlockClient client;

  private AmazonS3 mAwsClient;

  @BeforeEach
  public void init() {
    mAwsClient = mock(AmazonS3.class);

    client = new AwsPublicAccessBlockClient(mAwsClient);
  }

  @Test
  public void testGet_ReturnsIaasPublicAccessBlock() {
    doReturn(new GetPublicAccessBlockResult()
        .withPublicAccessBlockConfiguration(new PublicAccessBlockConfiguration())).when(mAwsClient)
            .getPublicAccessBlock(any(GetPublicAccessBlockRequest.class));

    IaasObjectStoreAccessConfig config = client.get("BUCKET_1");

    assertEquals("BUCKET_1", config.getObjectStoreName());
    assertTrue(new ReflectionEquals(new PublicAccessBlockConfiguration())
        .matches(config.getPublicAccessBlockConfig()));
  }

  @Test
  public void testGet_ReturnsNull_WhenClientThrowsException() {
    doThrow(AmazonS3Exception.class).when(mAwsClient)
        .getPublicAccessBlock(any(GetPublicAccessBlockRequest.class));

    assertNull(client.get("BUCKET_1"));
  }

  @Test
  public void testAdd_ReturnsAddedPublicAccessBlock() {
    client = spy(client);
    doReturn(new IaasObjectStoreAccessConfig("BUCKET_1", new PublicAccessBlockConfiguration()))
        .when(client).get("BUCKET_1");

    IaasObjectStoreAccessConfig config = client
        .add(new IaasObjectStoreAccessConfig("BUCKET_1", new PublicAccessBlockConfiguration()));

    assertEquals("BUCKET_1", config.getObjectStoreName());
    assertTrue(new ReflectionEquals(new PublicAccessBlockConfiguration())
        .matches(config.getPublicAccessBlockConfig()));
    ArgumentCaptor<SetPublicAccessBlockRequest> argument
        = ArgumentCaptor.forClass(SetPublicAccessBlockRequest.class);
    verify(mAwsClient).setPublicAccessBlock(argument.capture());
    assertEquals("BUCKET_1", argument.getValue().getBucketName());
    assertTrue(new ReflectionEquals(new PublicAccessBlockConfiguration())
        .matches(argument.getValue().getPublicAccessBlockConfiguration()));
  }

  @Test
  public void testPut_ReturnsEntity_WhenGetReturnsEntity() {
    client = spy(client);
    doReturn(new IaasObjectStoreAccessConfig("BUCKET_1", new PublicAccessBlockConfiguration()))
        .when(client).get("BUCKET_1");

    IaasObjectStoreAccessConfig config = client
        .put(new IaasObjectStoreAccessConfig("BUCKET_1", new PublicAccessBlockConfiguration()));

    assertEquals("BUCKET_1", config.getObjectStoreName());
    assertTrue(new ReflectionEquals(new PublicAccessBlockConfiguration())
        .matches(config.getPublicAccessBlockConfig()));
  }

  @Test
  public void testExists_ReturnsFalse_WhenGetReturnsNull() {
    client = spy(client);
    doReturn(null).when(client).get("BUCKET_1");

    assertFalse(client.exists("BUCKET_1"));
  }

  @Test
  public void testExists_ReturnsTrue_WhenGetReturnsEntity() {
    client = spy(client);
    doReturn(new IaasObjectStoreAccessConfig("BUCKET_1", new PublicAccessBlockConfiguration()))
        .when(client).get("BUCKET_1");

    assertTrue(client.exists("BUCKET_1"));
  }

  @Test
  public void testDelete_ReturnsTrue_WhenDeleteIsSuccessful() {
    doReturn(null).when(mAwsClient)
        .deletePublicAccessBlock(new DeletePublicAccessBlockRequest().withBucketName("BUCKET_1"));

    assertTrue(client.delete("BUCKET_1"));

    verify(mAwsClient, times(1))
        .deletePublicAccessBlock(new DeletePublicAccessBlockRequest().withBucketName("BUCKET_1"));
  }

  @Test
  public void testDelete_ReturnsFalse_WhenEntityDoesNotExist() {
    doThrow(AmazonS3Exception.class).when(mAwsClient)
        .deletePublicAccessBlock(new DeletePublicAccessBlockRequest().withBucketName("BUCKET_1"));
    assertFalse(client.delete("BUCKET_1"));
  }
}
