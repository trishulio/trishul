package io.trishul.iaas.user.aws.model;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;
import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.amazonaws.services.cognitoidp.model.AdminGetUserResult;
import com.amazonaws.services.cognitoidp.model.AttributeType;

import io.trishul.iaas.user.model.IaasUser;
import io.trishul.auth.aws.session.context.CognitoPrincipalContext;

public class AwsCognitoAdminGetUserResultMapperTest {
    private AwsCognitoAdminGetUserResultMapper mapper;

    @BeforeEach
    public void init() {
        mapper = AwsCognitoAdminGetUserResultMapper.INSTANCE;
    }

    @Test
    public void testFromIaasEntity_ReturnsNull_WhenArgIsNull() {
        assertNull(mapper.fromIaasEntity(null));
    }

    @Test
    public void testFromIaasEntity_ReturnsEntity_WhenArgIsNotNull() {
        AdminGetUserResult user = new AdminGetUserResult()
                .withUserAttributes(new AttributeType().withName(CognitoPrincipalContext.ATTRIBUTE_EMAIL).withValue("EMAIL"))
                .withUserCreateDate(new Date(100, 0, 1))
                .withUserLastModifiedDate(new Date(101, 0, 1))
                .withUsername("USERNAME");

        IaasUser iaasUser = mapper.fromIaasEntity(user);

        IaasUser expected = new IaasUser(null, "EMAIL", null, LocalDateTime.of(2000, 1, 1, 0, 0), LocalDateTime.of(2001, 1, 1, 0, 0));
        assertEquals(expected, iaasUser);
    }

    @Test
    public void testFromIaasEntity_ReturnsEntityWithoutEmail_WhenAttributesAreNull() {
        AdminGetUserResult user = new AdminGetUserResult()
                .withUserCreateDate(new Date(100, 0, 1))
                .withUserLastModifiedDate(new Date(101, 0, 1))
                .withUsername("EMAIL");

        IaasUser iaasUser = mapper.fromIaasEntity(user);

        IaasUser expected = new IaasUser(null, "EMAIL", null, LocalDateTime.of(2000, 1, 1, 0, 0), LocalDateTime.of(2001, 1, 1, 0, 0));
        assertEquals(expected, iaasUser);
    }
}