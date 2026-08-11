import re

with open('modules/trishul-iaas-access/src/test/java/io/trishul/iaas/access/role/attachment/policy/IaasRolePolicyAttachmentTest.java', 'r') as f:
    c = f.read()
c = re.sub(
    r'IaasRole value[\s\S]*?org.mockito.Mockito.RETURNS_DEEP_STUBS\);',
    'IaasRole value = new IaasRole();',
    c
)
c = re.sub(
    r'IaasPolicy value[\s\S]*?org.mockito.Mockito.RETURNS_DEEP_STUBS\);',
    'IaasPolicy value = new IaasPolicy();',
    c
)
with open('modules/trishul-iaas-access/src/test/java/io/trishul/iaas/access/role/attachment/policy/IaasRolePolicyAttachmentTest.java', 'w') as f:
    f.write(c)

