import re

with open('modules/trishul-iaas-access/src/test/java/sh/trishul/iaas/access/role/attachment/policy/IaasRolePolicyAttachmentTest.java', 'r') as f:
    c = f.read()
c = c.replace(
    'new IaasRolePolicyAttachmentId(java.util.UUID.randomUUID(), java.util.UUID.randomUUID());',
    'new IaasRolePolicyAttachmentId("role-1", "policy-1");'
)
with open('modules/trishul-iaas-access/src/test/java/sh/trishul/iaas/access/role/attachment/policy/IaasRolePolicyAttachmentTest.java', 'w') as f:
    f.write(c)

with open('modules/trishul-iaas-user/src/test/java/sh/trishul/iaas/user/model/IaasUserTenantMembershipTest.java', 'r') as f:
    c = f.read()
c = c.replace(
    'new IaasUserTenantMembershipId(java.util.UUID.randomUUID(), java.util.UUID.randomUUID());',
    'new IaasUserTenantMembershipId("user-1", "tenant-1");'
)
with open('modules/trishul-iaas-user/src/test/java/sh/trishul/iaas/user/model/IaasUserTenantMembershipTest.java', 'w') as f:
    f.write(c)

