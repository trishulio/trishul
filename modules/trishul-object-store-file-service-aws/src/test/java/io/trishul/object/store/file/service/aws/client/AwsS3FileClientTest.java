package io.trishul.object.store.file.service.aws.client;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import io.trishul.model.mapper.LocalDateTimeMapper;
import io.trishul.object.store.file.model.IaasObjectStoreFile;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

public class AwsS3FileClientTest {
  private AwsS3FileClient client;

  private AmazonS3 mS3;

  @BeforeEach
  public void init() {
    mS3 = mock(AmazonS3.class);
    client = new AwsS3FileClient(mS3, "BUCKET_NAME", LocalDateTimeMapper.INSTANCE, 1000);
  }

  @Test
  public void testGet_ReturnsObjectStoreWithGetPresignedURL() throws MalformedURLException {
    ArgumentCaptor<GeneratePresignedUrlRequest> captor
        = ArgumentCaptor.forClass(GeneratePresignedUrlRequest.class);

    doAnswer(inv -> {
      GeneratePresignedUrlRequest req = inv.getArgument(0, GeneratePresignedUrlRequest.class);
      return new URL("http://localhost/" + req.getKey());
    }).when(mS3).generatePresignedUrl(captor.capture());

    IaasObjectStoreFile file = client.get(URI.create("file.txt"));

    assertEquals(URI.create("file.txt"), file.getFileKey());
    assertThat(file.getExpiration()).isBetween(LocalDateTime.now(),
        LocalDateTime.now().plusSeconds(1005));
    assertEquals(new URL("http://localhost/file.txt"), file.getFileUrl());

    assertEquals(HttpMethod.GET, captor.getValue().getMethod());
  }

  @Test
  public void testAdd_ReturnsObjectStoreWithPutPresignedURLAndRandomFileKey() {
    ArgumentCaptor<GeneratePresignedUrlRequest> captor
        = ArgumentCaptor.forClass(GeneratePresignedUrlRequest.class);

    doAnswer(inv -> {
      GeneratePresignedUrlRequest req = inv.getArgument(0, GeneratePresignedUrlRequest.class);
      return new URL("http://localhost/" + req.getKey());
    }).when(mS3).generatePresignedUrl(captor.capture());

    IaasObjectStoreFile file
        = client.add(new IaasObjectStoreFile().setExpiration(LocalDateTime.of(2000, 1, 1, 0, 0)));

    assertNotNull(file.getFileKey());
    assertEquals(LocalDateTime.of(2000, 1, 1, 0, 0), file.getExpiration());
    assertThat(file.getFileUrl().toString()).startsWith("http://localhost/");
    assertThat(file.getFileUrl().toString()).endsWith(file.getFileKey().toString());

    assertEquals(HttpMethod.PUT, captor.getValue().getMethod());
  }

  @Test
  public void testPut_ReturnsObjectStoreWithPutPresignedURLAndGivenFileKey()
      throws MalformedURLException {
    ArgumentCaptor<GeneratePresignedUrlRequest> captor
        = ArgumentCaptor.forClass(GeneratePresignedUrlRequest.class);

    doAnswer(inv -> {
      GeneratePresignedUrlRequest req = inv.getArgument(0, GeneratePresignedUrlRequest.class);
      return new URL("http://localhost/" + req.getKey());
    }).when(mS3).generatePresignedUrl(captor.capture());

    IaasObjectStoreFile file = client.put(new IaasObjectStoreFile()
        .setFileKey(URI.create("note.txt")).setExpiration(LocalDateTime.of(2000, 1, 1, 0, 0)));

    assertEquals(URI.create("note.txt"), file.getFileKey());
    assertEquals(LocalDateTime.of(2000, 1, 1, 0, 0), file.getExpiration());
    assertEquals(new URL("http://localhost/note.txt"), file.getFileUrl());

    assertEquals(HttpMethod.PUT, captor.getValue().getMethod());
  }

  @Test
  public void testDelete_ReturnTrueAndCallsDelete_WhenExistsReturnTrue()
      throws MalformedURLException {
    ArgumentCaptor<DeleteObjectRequest> captor = ArgumentCaptor.forClass(DeleteObjectRequest.class);
    doNothing().when(mS3).deleteObject(captor.capture());

    client = spy(client);
    doReturn(true).when(client).exists(URI.create("file.txt"));

    assertTrue(client.delete(URI.create("file.txt")));

    assertEquals(captor.getValue().getBucketName(), "BUCKET_NAME");
    assertEquals(captor.getValue().getKey(), "file.txt");
    verify(mS3, times(1)).deleteObject(any(DeleteObjectRequest.class));
  }

  @Test
  public void testDelete_ReturnFalseAndDoNothing_WhenExistsReturnFalse()
      throws MalformedURLException {
    client = spy(client);
    doReturn(false).when(client).exists(URI.create("file.txt"));

    assertFalse(client.delete(URI.create("file.txt")));

    verify(mS3, times(0)).deleteObject(any());
    verify(mS3, times(0)).deleteObject(any(), any());
  }

  @Test
  public void testExists_ReturnsTrue_WhenS3ReturnsTrue() {
    doReturn(true).when(mS3).doesObjectExist("BUCKET_NAME", "file.txt");

    assertTrue(client.exists(URI.create("file.txt")));
  }

  @Test
  public void testExists_ReturnsFalse_WhenS3ReturnsFalse() {
    doReturn(false).when(mS3).doesObjectExist("BUCKET_NAME", "file.txt");

    assertFalse(client.exists(URI.create("file.txt")));
  }
}
