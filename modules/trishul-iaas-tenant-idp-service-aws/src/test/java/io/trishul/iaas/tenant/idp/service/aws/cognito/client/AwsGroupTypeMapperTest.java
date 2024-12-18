package io.trishul.iaas.tenant.idp.service.aws.cognito.client;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import com.amazonaws.services.cognitoidp.model.GroupType;
import io.trishul.iaas.idp.tenant.model.IaasIdpTenant;
import java.time.LocalDateTime;
import java.util.Date;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AwsGroupTypeMapperTest {
    private AwsGroupTypeMapper mapper;

    @BeforeEach
    public void init() {
        mapper = AwsGroupTypeMapper.INSTANCE;
    }

    @Test
    public void testFromIaasEntity_ReturnsNull_WhenArgIsNull() {
        assertNull(mapper.fromIaasEntity(null));
    }

    @Test
    public void testFromIaasEntity_ReturnsEntity_WhenArgIsNotNull() {
        GroupType group =
                new GroupType()
                        .withGroupName("GROUP")
                        .withDescription("DESCRIPTION")
                        .withCreationDate(new Date(100, 0, 1))
                        .withLastModifiedDate(new Date(101, 0, 1));

        IaasIdpTenant iaasTenant = mapper.fromIaasEntity(group);

        IaasIdpTenant expected =
                new IaasIdpTenant(
                        "GROUP",
                        null,
                        "DESCRIPTION",
                        LocalDateTime.of(2000, 1, 1, 0, 0),
                        LocalDateTime.of(2001, 1, 1, 0, 0));
        assertEquals(expected, iaasTenant);
    }
}
