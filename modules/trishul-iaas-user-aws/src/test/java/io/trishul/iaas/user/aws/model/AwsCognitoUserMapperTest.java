package io.company.brewcraft.service.mapper;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;
import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.amazonaws.services.cognitoidp.model.AttributeType;
import com.amazonaws.services.cognitoidp.model.UserType;

import io.company.brewcraft.model.IaasUser;
import io.trishul.auth.aws.session.context.CognitoPrincipalContext;

public class AwsCognitoUserMapperTest {
    private AwsCognitoUserMapper mapper;

    @BeforeEach
    public void init() {
        mapper = AwsCognitoUserMapper.INSTANCE;
    }

    @Test
    public void testFromIaasEntity_ReturnsNull_WhenArgIsNull() {
        assertNull(mapper.fromIaasEntity(null));
    }

    @Test
    public void testFromIaasEntity_ReturnsEntity_WhenArgIsNotNull() {
        UserType user = new UserType()
                .withAttributes(new AttributeType().withName(CognitoPrincipalContext.ATTRIBUTE_EMAIL).withValue("EMAIL"))
                .withUserCreateDate(new Date(100, 0, 1))
                .withUserLastModifiedDate(new Date(101, 0, 1))
                .withUsername("USERNAME");

        IaasUser iaasUser = mapper.fromIaasEntity(user);

        IaasUser expected = new IaasUser(null, "EMAIL", null, LocalDateTime.of(2000, 1, 1, 0, 0), LocalDateTime.of(2001, 1, 1, 0, 0));
        assertEquals(expected, iaasUser);
    }

    @Test
    public void testFromIaasEntity_ReturnsEntityWithEmailSetFromUsername() {
        UserType user = new UserType()
                .withUserCreateDate(new Date(100, 0, 1))
                .withUserLastModifiedDate(new Date(101, 0, 1))
                .withUsername("USERNAME");

        IaasUser iaasUser = mapper.fromIaasEntity(user);

        IaasUser expected = new IaasUser(null, "USERNAME", null, LocalDateTime.of(2000, 1, 1, 0, 0), LocalDateTime.of(2001, 1, 1, 0, 0));
        assertEquals(expected, iaasUser);
    }
}
