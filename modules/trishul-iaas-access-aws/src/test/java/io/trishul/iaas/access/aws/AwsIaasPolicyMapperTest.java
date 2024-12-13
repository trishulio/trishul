package io.trishul.iaas.access.aws;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;
import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.amazonaws.services.identitymanagement.model.Policy;

import io.trishul.iaas.access.policy.model.IaasPolicy;

public class AwsIaasPolicyMapperTest {
    private AwsIaasPolicyMapper mapper;

    @BeforeEach
    public void init() {
        mapper = AwsIaasPolicyMapper.INSTANCE;
    }

    @Test
    public void testFromIaasEntity_ReturnsNull_WhenArgIsNull() {
        assertNull(mapper.fromIaasEntity(null));
    }

    @Test
    public void testFromIaasEntity_ReturnsEntity_WhenArgIsNotNull() {
        Policy arg = new Policy()
                        .withArn("POLICY_ARN")
                        .withCreateDate(new Date(1, 1, 1))
                        .withDescription("POLICY_DESCRIPTION")
                        .withPath("POLICY_PATH")
                        .withPolicyId("POLICY_IAAS_ID")
                        .withPolicyName("POLICY_NAME")
                        .withUpdateDate(new Date(2, 2, 2));

        IaasPolicy policy = mapper.fromIaasEntity(arg);

        IaasPolicy expected = new IaasPolicy("POLICY_NAME", null, "POLICY_DESCRIPTION", "POLICY_ARN", "POLICY_IAAS_ID", LocalDateTime.of(1901, 2, 1, 0, 0), LocalDateTime.of(1902, 3, 2, 0, 0));
        assertEquals(expected, policy);
    }
}
