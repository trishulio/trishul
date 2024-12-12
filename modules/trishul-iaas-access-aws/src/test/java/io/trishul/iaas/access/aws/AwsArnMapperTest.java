package io.company.brewcraft.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AwsArnMapperTest {
    private AwsArnMapper mapper;

    @BeforeEach
    public void init() {
        mapper = new AwsArnMapper("ACCOUNT", "PARTITION");
    }

    @Test
    public void testGetPolicyArn_ReturnAwsArn() {
        String policyArn = mapper.getPolicyArn("POLICY_NAME");

        assertEquals("arn:partition:iam::account:policy/policy_name", policyArn);
    }

    @Test
    public void testGetRoleArn_ReturnAwsRoleArn() {
        String roleArn = mapper.getRoleArn("ROLE_NAME");

        assertEquals("arn:partition:iam::account:role/role_name", roleArn);
    }

    @Test
    public void testGetName_ReturnsResourceName_WhenArnIsValid() {
        String arn = "arn:partition:iam::account:policy/policy_name";

        String resName = mapper.getName(arn);
        assertEquals("policy_name", resName);
    }
}
