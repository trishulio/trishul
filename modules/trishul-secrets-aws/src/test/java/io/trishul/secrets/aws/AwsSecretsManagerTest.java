package io.trishul.secrets.aws;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import com.amazonaws.ResponseMetadata;
import com.amazonaws.services.secretsmanager.AWSSecretsManager;
import com.amazonaws.services.secretsmanager.model.CreateSecretRequest;
import com.amazonaws.services.secretsmanager.model.DeleteSecretRequest;
import com.amazonaws.services.secretsmanager.model.DeleteSecretResult;
import com.amazonaws.services.secretsmanager.model.GetSecretValueRequest;
import com.amazonaws.services.secretsmanager.model.GetSecretValueResult;
import com.amazonaws.services.secretsmanager.model.InvalidParameterException;
import com.amazonaws.services.secretsmanager.model.InvalidRequestException;
import com.amazonaws.services.secretsmanager.model.PutSecretValueRequest;
import com.amazonaws.services.secretsmanager.model.ResourceNotFoundException;
import com.amazonaws.services.secretsmanager.model.UpdateSecretRequest;
import java.io.IOException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AwsSecretsManagerTest {
  private AwsSecretsManager secretsManager;
  private AWSSecretsManager mockClient;

  @BeforeEach
  void init() {
    mockClient = mock(AWSSecretsManager.class);
    secretsManager = new AwsSecretsManager(mockClient);
  }

  @Test
  void testGet_ReturnsSecretString_WhenSecretExists() throws IOException {
    GetSecretValueResult result = new GetSecretValueResult().withSecretString("secret-value");
    doReturn(result).when(mockClient).getSecretValue(any(GetSecretValueRequest.class));

    String secret = secretsManager.get("test-secret-id");

    assertEquals("secret-value", secret);
  }

  @Test
  void testGet_ReturnsNull_WhenResultIsNull() throws IOException {
    doReturn(null).when(mockClient).getSecretValue(any(GetSecretValueRequest.class));

    String secret = secretsManager.get("test-secret-id");

    assertNull(secret);
  }

  @Test
  void testGet_ReturnsNull_WhenSecretStringIsNull() throws IOException {
    GetSecretValueResult result = new GetSecretValueResult();
    doReturn(result).when(mockClient).getSecretValue(any(GetSecretValueRequest.class));

    String secret = secretsManager.get("test-secret-id");

    assertNull(secret);
  }

  @Test
  void testGet_ReturnsNull_WhenResourceNotFound() throws IOException {
    doThrow(new ResourceNotFoundException("not found")).when(mockClient)
        .getSecretValue(any(GetSecretValueRequest.class));

    String secret = secretsManager.get("test-secret-id");

    assertNull(secret);
  }

  @Test
  void testGet_ThrowsIOException_WhenInvalidRequestException() {
    doThrow(new InvalidRequestException("invalid")).when(mockClient)
        .getSecretValue(any(GetSecretValueRequest.class));

    assertThrows(IOException.class, () -> secretsManager.get("test-secret-id"));
  }

  @Test
  void testGet_ThrowsIOException_WhenInvalidParameterException() {
    doThrow(new InvalidParameterException("invalid")).when(mockClient)
        .getSecretValue(any(GetSecretValueRequest.class));

    assertThrows(IOException.class, () -> secretsManager.get("test-secret-id"));
  }

  @Test
  void testExists_ReturnsTrue_WhenSecretExists() throws IOException {
    GetSecretValueResult result = new GetSecretValueResult().withSecretString("secret-value");
    doReturn(result).when(mockClient).getSecretValue(any(GetSecretValueRequest.class));

    Boolean exists = secretsManager.exists("test-secret-id");

    assertTrue(exists);
  }

  @Test
  void testExists_ReturnsFalse_WhenResultIsNull() throws IOException {
    doReturn(null).when(mockClient).getSecretValue(any(GetSecretValueRequest.class));

    Boolean exists = secretsManager.exists("test-secret-id");

    assertFalse(exists);
  }

  @Test
  void testExists_ReturnsFalse_WhenSecretStringIsNull() throws IOException {
    GetSecretValueResult result = new GetSecretValueResult();
    doReturn(result).when(mockClient).getSecretValue(any(GetSecretValueRequest.class));

    Boolean exists = secretsManager.exists("test-secret-id");

    assertFalse(exists);
  }

  @Test
  void testExists_ReturnsFalse_WhenResourceNotFound() throws IOException {
    doThrow(new ResourceNotFoundException("not found")).when(mockClient)
        .getSecretValue(any(GetSecretValueRequest.class));

    Boolean exists = secretsManager.exists("test-secret-id");

    assertFalse(exists);
  }

  @Test
  void testExists_ThrowsIOException_WhenInvalidRequestException() {
    doThrow(new InvalidRequestException("invalid")).when(mockClient)
        .getSecretValue(any(GetSecretValueRequest.class));

    assertThrows(IOException.class, () -> secretsManager.exists("test-secret-id"));
  }

  @Test
  void testExists_ThrowsIOException_WhenInvalidParameterException() {
    doThrow(new InvalidParameterException("invalid")).when(mockClient)
        .getSecretValue(any(GetSecretValueRequest.class));

    assertThrows(IOException.class, () -> secretsManager.exists("test-secret-id"));
  }

  @Test
  void testPut_CallsCreateSecret_WhenSecretDoesNotExist() throws IOException {
    doThrow(new ResourceNotFoundException("not found")).when(mockClient)
        .getSecretValue(any(GetSecretValueRequest.class));

    secretsManager.put("test-secret-id", "new-secret");

    verify(mockClient).createSecret(any(CreateSecretRequest.class));
  }

  @Test
  void testPut_CallsPutSecretValue_WhenSecretExists() throws IOException {
    GetSecretValueResult result = new GetSecretValueResult().withSecretString("old-secret");
    doReturn(result).when(mockClient).getSecretValue(any(GetSecretValueRequest.class));

    secretsManager.put("test-secret-id", "updated-secret");

    verify(mockClient).putSecretValue(any(PutSecretValueRequest.class));
  }

  @Test
  void testPut_ThrowsIOException_WhenPutSecretValueThrowsInvalidRequestException()
      throws IOException {
    GetSecretValueResult result = new GetSecretValueResult().withSecretString("old-secret");
    doReturn(result).when(mockClient).getSecretValue(any(GetSecretValueRequest.class));
    doThrow(new InvalidRequestException("invalid")).when(mockClient)
        .putSecretValue(any(PutSecretValueRequest.class));

    assertThrows(IOException.class, () -> secretsManager.put("test-secret-id", "new-secret"));
  }

  @Test
  void testPut_ThrowsIOException_WhenPutSecretValueThrowsInvalidParameterException()
      throws IOException {
    GetSecretValueResult result = new GetSecretValueResult().withSecretString("old-secret");
    doReturn(result).when(mockClient).getSecretValue(any(GetSecretValueRequest.class));
    doThrow(new InvalidParameterException("invalid")).when(mockClient)
        .putSecretValue(any(PutSecretValueRequest.class));

    assertThrows(IOException.class, () -> secretsManager.put("test-secret-id", "new-secret"));
  }

  @Test
  void testCreate_CallsCreateSecret() throws IOException {
    secretsManager.create("test-secret-id", "secret-value");

    verify(mockClient).createSecret(any(CreateSecretRequest.class));
  }

  @Test
  void testCreate_ThrowsIOException_WhenInvalidRequestException() {
    doThrow(new InvalidRequestException("invalid")).when(mockClient)
        .createSecret(any(CreateSecretRequest.class));

    assertThrows(IOException.class, () -> secretsManager.create("test-secret-id", "secret-value"));
  }

  @Test
  void testCreate_ThrowsIOException_WhenInvalidParameterException() {
    doThrow(new InvalidParameterException("invalid")).when(mockClient)
        .createSecret(any(CreateSecretRequest.class));

    assertThrows(IOException.class, () -> secretsManager.create("test-secret-id", "secret-value"));
  }

  @Test
  void testUpdate_CallsUpdateSecret() throws IOException {
    secretsManager.update("test-secret-id", "updated-secret");

    verify(mockClient).updateSecret(any(UpdateSecretRequest.class));
  }

  @Test
  void testUpdate_ThrowsIOException_WhenInvalidRequestException() {
    doThrow(new InvalidRequestException("invalid")).when(mockClient)
        .updateSecret(any(UpdateSecretRequest.class));

    assertThrows(IOException.class,
        () -> secretsManager.update("test-secret-id", "updated-secret"));
  }

  @Test
  void testUpdate_ThrowsIOException_WhenInvalidParameterException() {
    doThrow(new InvalidParameterException("invalid")).when(mockClient)
        .updateSecret(any(UpdateSecretRequest.class));

    assertThrows(IOException.class,
        () -> secretsManager.update("test-secret-id", "updated-secret"));
  }

  @Test
  void testRemove_ReturnsTrue_WhenDeleteSucceeds() throws IOException {
    DeleteSecretResult result = mock(DeleteSecretResult.class);
    ResponseMetadata metadata = mock(ResponseMetadata.class);
    doReturn("request-123").when(metadata).getRequestId();
    doReturn(metadata).when(result).getSdkResponseMetadata();
    doReturn(result).when(mockClient).deleteSecret(any(DeleteSecretRequest.class));

    boolean success = secretsManager.remove("test-secret-id");

    assertTrue(success);
    verify(mockClient).deleteSecret(any(DeleteSecretRequest.class));
  }

  @Test
  void testRemove_ReturnsFalse_WhenResourceNotFound() throws IOException {
    doThrow(new ResourceNotFoundException("not found")).when(mockClient)
        .deleteSecret(any(DeleteSecretRequest.class));

    boolean success = secretsManager.remove("test-secret-id");

    assertFalse(success);
  }
}
