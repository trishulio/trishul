package io.trishul.iaas.user.service.aws;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.function.Supplier;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import com.amazonaws.ResponseMetadata;
import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProvider;
import com.amazonaws.services.cognitoidp.model.AdminCreateUserRequest;
import com.amazonaws.services.cognitoidp.model.AdminCreateUserResult;
import com.amazonaws.services.cognitoidp.model.AdminDeleteUserResult;
import com.amazonaws.services.cognitoidp.model.AdminGetUserRequest;
import com.amazonaws.services.cognitoidp.model.AdminGetUserResult;
import com.amazonaws.services.cognitoidp.model.AdminUpdateUserAttributesRequest;
import com.amazonaws.services.cognitoidp.model.AdminUpdateUserAttributesResult;
import com.amazonaws.services.cognitoidp.model.AttributeType;
import com.amazonaws.services.cognitoidp.model.DeliveryMediumType;
import com.amazonaws.services.cognitoidp.model.UserNotFoundException;
import com.amazonaws.services.cognitoidp.model.UserType;
import io.trishul.auth.aws.session.context.CognitoPrincipalContext;
import io.trishul.iaas.user.aws.model.AwsCognitoAdminGetUserResultMapper;
import io.trishul.iaas.user.aws.model.AwsCognitoUserMapper;
import io.trishul.iaas.user.model.IaasUser;

public class AwsCognitoUserClientTest {
  private AwsCognitoUserClient client;

  private AWSCognitoIdentityProvider mIdp;

  @BeforeEach
  public void init() {
    mIdp = mock(AWSCognitoIdentityProvider.class);
    client = new AwsCognitoUserClient(mIdp, "USER_POOL_ID", "TEMPORARY_PASSWORD",
        AwsCognitoAdminGetUserResultMapper.INSTANCE, AwsCognitoUserMapper.INSTANCE);
  }

  private ResponseMetadata mockResponseMetadata() {
    ResponseMetadata metadata = mock(ResponseMetadata.class);
    doReturn("mock-request-id").when(metadata).getRequestId();
    return metadata;
  }

  @Test
  public void testGet_ReturnsUserFromResult_WhenClientReturnsResult() {
    doAnswer(inv -> {
      AdminGetUserRequest req = inv.getArgument(0, AdminGetUserRequest.class);
      return new AdminGetUserResult()
          .withUserAttributes(new AttributeType().withName(CognitoPrincipalContext.ATTRIBUTE_EMAIL)
              .withValue("EMAIL"))
          .withUserCreateDate(new Date(100, 0, 1)).withUserLastModifiedDate(new Date(100, 1, 2))
          .withUsername(req.getUsername());
    }).when(mIdp).adminGetUser(any());

    IaasUser user = client.get("USERNAME");

    IaasUser expected
        = new IaasUser().setEmail("EMAIL").setCreatedAt(LocalDateTime.of(2000, 1, 1, 0, 0))
            .setLastUpdated(LocalDateTime.of(2000, 2, 2, 0, 0));
    assertEquals(expected, user);
  }

  @Test
  public void testGet_ReturnsNull_WhenClientThrowsNotFoundException() {
    doThrow(UserNotFoundException.class).when(mIdp).adminGetUser(any());

    assertNull(client.get("USERNAME"));
  }

  @Test
  public void testAdd_AddsAndReturnsUser() {
    doAnswer(inv -> {
      AdminCreateUserRequest req = inv.getArgument(0, AdminCreateUserRequest.class);

      assertEquals(List.of(DeliveryMediumType.EMAIL.toString()), req.getDesiredDeliveryMediums());
      UserType userType = new UserType().withAttributes(req.getUserAttributes())
          .withUserCreateDate(new Date(100, 0, 1)).withUserLastModifiedDate(new Date(100, 1, 2))
          .withUsername(req.getUsername());
      AdminCreateUserResult result = new AdminCreateUserResult().withUser(userType);
      result.setSdkResponseMetadata(mockResponseMetadata());
      return result;
    }).when(mIdp).adminCreateUser(any());

    IaasUser user = client.add(new IaasUser().setId("USERNAME").setEmail("EMAIL")
        .setCreatedAt(LocalDateTime.of(2000, 1, 1, 0, 0))
        .setLastUpdated(LocalDateTime.of(2000, 2, 2, 0, 0)));

    IaasUser expected
        = new IaasUser().setEmail("EMAIL").setCreatedAt(LocalDateTime.of(2000, 1, 1, 0, 0))
            .setLastUpdated(LocalDateTime.of(2000, 2, 2, 0, 0));
    assertEquals(expected, user);
  }

  @Test
  public void testUpdate_UpdatesAttributesAndReturnsUser() {
    class AttributeSupplier implements Supplier<List<AttributeType>> {
      private List<AttributeType> attributes;

      @Override
      public List<AttributeType> get() {
        return this.attributes;
      }

      public void setAttributes(List<AttributeType> attributes) {
        this.attributes = attributes;
      }
    }

    AttributeSupplier attributesSupplier = new AttributeSupplier();
    doAnswer(inv -> {
      AdminUpdateUserAttributesRequest req
          = inv.getArgument(0, AdminUpdateUserAttributesRequest.class);
      attributesSupplier.setAttributes(req.getUserAttributes());
      AdminUpdateUserAttributesResult result = new AdminUpdateUserAttributesResult();
      result.setSdkResponseMetadata(mockResponseMetadata());
      return result;
    }).when(mIdp).adminUpdateUserAttributes(any());

    doAnswer(inv -> {
      AdminGetUserRequest req = inv.getArgument(0, AdminGetUserRequest.class);
      return new AdminGetUserResult().withUserAttributes(attributesSupplier.get())
          .withUserCreateDate(new Date(100, 0, 1)).withUserLastModifiedDate(new Date(100, 1, 2))
          .withUsername(req.getUsername());
    }).when(mIdp).adminGetUser(any());

    IaasUser user = client.update(new IaasUser().setId("USERNAME").setEmail("EMAIL")
        .setCreatedAt(LocalDateTime.of(2000, 1, 1, 0, 0))
        .setLastUpdated(LocalDateTime.of(2000, 2, 2, 0, 0)));

    IaasUser expected
        = new IaasUser().setEmail("EMAIL").setCreatedAt(LocalDateTime.of(2000, 1, 1, 0, 0))
            .setLastUpdated(LocalDateTime.of(2000, 2, 2, 0, 0));
    assertEquals(expected, user);

    verify(mIdp).adminUpdateUserAttributes(any());
  }

  @Test
  public void testDelete_ReturnsTrue_WhenNoExceptionIsThrown() {
    doAnswer(inv -> {
      AdminDeleteUserResult result = new AdminDeleteUserResult();
      result.setSdkResponseMetadata(mockResponseMetadata());
      return result;
    }).when(mIdp).adminDeleteUser(any());

    assertTrue(client.delete("USERNAME"));
  }

  @Test
  public void testDelete_ReturnsFalse_WhenUserNotFoundExceptionIsThrown() {
    doThrow(UserNotFoundException.class).when(mIdp).adminDeleteUser(any());

    assertFalse(client.delete("USERNAME"));
  }

  @Test
  public void testExists_ReturnsTrue_WhenGetIsNotNull() {
    client = spy(client);
    doReturn(new IaasUser()).when(client).get("USERNAME");

    assertTrue(client.exists("USERNAME"));
  }

  @Test
  public void testExists_ReturnsFalse_WhenGetIsNull() {
    client = spy(client);
    doReturn(null).when(client).get("USERNAME");

    assertFalse(client.exists("USERNAME"));
  }

  @Test
  public void testPut_CallsAdd_WhenExistIsFalse() {
    client = spy(client);
    doReturn(false).when(client).exists("USERNAME");

    doAnswer(inv -> inv.getArgument(0, IaasUser.class)).when(client).add(any());

    IaasUser user = client.put(new IaasUser("USERNAME"));

    IaasUser expected = new IaasUser().setId("USERNAME");
    assertEquals(expected, user);
  }

  @Test
  public void testPut_CallsUpdate_WhenExistIsTrue() {
    client = spy(client);
    doReturn(true).when(client).exists("USER_EMAIL");

    doAnswer(inv -> inv.getArgument(0, IaasUser.class)).when(client).update(any());

    IaasUser user = client.put(new IaasUser().setId("USERNAME").setEmail("USER_EMAIL"));

    IaasUser expected = new IaasUser().setId("USERNAME").setEmail("USER_EMAIL");
    assertEquals(expected, user);
  }
}
