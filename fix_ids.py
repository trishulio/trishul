import re

with open('modules/trishul-iaas-access/src/test/java/sh/trishul/iaas/access/role/attachment/policy/IaasRolePolicyAttachmentTest.java', 'r') as f:
    c = f.read()
c = re.sub(
    r'IaasRolePolicyAttachmentId value = org.mockito.Mockito.mock\(IaasRolePolicyAttachmentId.class,[\s\S]*?org.mockito.Mockito.RETURNS_DEEP_STUBS\);',
    'IaasRolePolicyAttachmentId value = new IaasRolePolicyAttachmentId(java.util.UUID.randomUUID(), java.util.UUID.randomUUID());',
    c
)
with open('modules/trishul-iaas-access/src/test/java/sh/trishul/iaas/access/role/attachment/policy/IaasRolePolicyAttachmentTest.java', 'w') as f:
    f.write(c)

with open('modules/trishul-iaas-user/src/test/java/sh/trishul/iaas/user/model/IaasUserTenantMembershipTest.java', 'r') as f:
    c = f.read()
c = re.sub(
    r'IaasUserTenantMembershipId value = org.mockito.Mockito.mock\(IaasUserTenantMembershipId.class,[\s\S]*?org.mockito.Mockito.RETURNS_DEEP_STUBS\);',
    'IaasUserTenantMembershipId value = new IaasUserTenantMembershipId(java.util.UUID.randomUUID(), java.util.UUID.randomUUID());',
    c
)
with open('modules/trishul-iaas-user/src/test/java/sh/trishul/iaas/user/model/IaasUserTenantMembershipTest.java', 'w') as f:
    f.write(c)

