import os
from collections import defaultdict

# The list of classes from the grep output
classes = """
io.trishul.ai.agent.model.AddAiAgentConfigDto
io.trishul.ai.agent.model.AiAgentConfigDto
io.trishul.ai.agent.model.AiAgentGuardrail
io.trishul.ai.agent.model.AiAgentSkill
io.trishul.ai.agent.model.AiAgentTool
io.trishul.ai.agent.model.UpdateAiAgentConfigDto
io.trishul.ai.chat.model.AddAiChatModelConfigDto
io.trishul.ai.chat.model.AiChatModelConfigDto
io.trishul.ai.chat.model.UpdateAiChatModelConfigDto
io.trishul.ai.guardrail.model.AddAiGuardrailDto
io.trishul.ai.guardrail.model.AiGuardrailDto
io.trishul.ai.guardrail.model.UpdateAiGuardrailDto
io.trishul.ai.memory.model.AddAiChatMemoryConfigDto
io.trishul.ai.memory.model.AiChatMemoryConfigDto
io.trishul.ai.memory.model.UpdateAiChatMemoryConfigDto
io.trishul.ai.session.model.AddAiChatSessionDto
io.trishul.ai.session.model.AiChatSessionDto
io.trishul.ai.session.model.UpdateAiChatSessionDto
io.trishul.ai.skill.model.AddAiSkillDto
io.trishul.ai.skill.model.AiSkillDto
io.trishul.ai.skill.model.UpdateAiSkillDto
io.trishul.ai.speech.model.AddAiSpeechConfigDto
io.trishul.ai.speech.model.AiSpeechConfigDto
io.trishul.ai.speech.model.UpdateAiSpeechConfigDto
io.trishul.ai.tool.model.AddAiToolDto
io.trishul.ai.tool.model.AiToolDto
io.trishul.ai.tool.model.UpdateAiToolDto
io.trishul.data.datasource.configuration.model.MigrationConfiguration
io.trishul.iaas.access.policy.model.IaasPolicy
io.trishul.iaas.access.role.attachment.policy.IaasRolePolicyAttachment
io.trishul.iaas.access.role.attachment.policy.IaasRolePolicyAttachmentId
io.trishul.iaas.access.role.model.IaasRole
io.trishul.iaas.auth.session.context.IaasAuthorization
io.trishul.iaas.auth.session.context.IaasAuthorizationDto
io.trishul.iaas.idp.tenant.model.IaasIdpTenant
io.trishul.iaas.idp.tenant.model.TenantIaasAuthDeleteResult
io.trishul.iaas.idp.tenant.model.TenantIaasAuthResources
io.trishul.iaas.idp.tenant.model.TenantIaasIdpDeleteResult
io.trishul.iaas.idp.tenant.model.TenantIaasIdpResources
io.trishul.iaas.tenant.object.store.TenantIaasVfsDeleteResult
io.trishul.iaas.tenant.resource.TenantIaasResources
io.trishul.iaas.tenant.service.TenantIaasDeleteResult
io.trishul.iaas.user.model.IaasUser
io.trishul.iaas.user.model.IaasUserTenantMembership
io.trishul.integration.communication.model.AddIntegrationCommunicationConfigDto
io.trishul.integration.communication.model.IntegrationCommunicationConfig
io.trishul.integration.communication.model.IntegrationCommunicationConfigDto
io.trishul.integration.communication.model.UpdateIntegrationCommunicationConfigDto
io.trishul.integration.model.AddIntegrationDto
io.trishul.integration.model.Integration
io.trishul.integration.model.IntegrationDto
io.trishul.integration.model.UpdateIntegrationDto
io.trishul.model.base.dto.DeleteResultDto
io.trishul.model.base.pojo.DeleteResult
io.trishul.money.amount.model.AmountDto
io.trishul.money.dto.MoneyDto
io.trishul.money.tax.amount.TaxAmount
io.trishul.money.tax.amount.dto.TaxAmountDto
io.trishul.money.tax.dto.TaxDto
io.trishul.money.tax.rate.dto.TaxRateDto
io.trishul.object.store.configuration.access.model.IaasObjectStoreAccessConfig
io.trishul.object.store.configuration.cors.model.IaasObjectStoreCorsConfiguration
io.trishul.object.store.model.IaasObjectStore
io.trishul.object.store.file.model.IaasObjectStoreFile
io.trishul.quantity.model.QuantityEntity
io.trishul.quantity.model.dto.QuantityDto
io.trishul.quantity.unit.UnitEntity
io.trishul.quantity.unit.dto.UnitDto
io.trishul.repo.jpa.repository.model.dto.PageDto
io.trishul.tenant.dto.AddTenantDto
io.trishul.tenant.dto.TenantDto
io.trishul.tenant.dto.UpdateTenantDto
io.trishul.tenant.entity.AdminTenant
io.trishul.tenant.entity.Tenant
io.trishul.user.model.AddUserDto
io.trishul.user.model.UpdateUserDto
io.trishul.user.model.User
io.trishul.user.model.UserDto
io.trishul.user.role.binding.model.UserRoleBinding
io.trishul.user.salutation.model.UserSalutation
io.trishul.user.salutation.model.UserSalutationDto
io.trishul.user.status.UserStatus
io.trishul.user.status.UserStatusDto
"""

class_list = [c.strip() for c in classes.strip().split('\n') if c.strip()]
module_map = {}
for root, dirs, files in os.walk('modules'):
    for file in files:
        if file.endswith('.java'):
            path = os.path.join(root, file)
            # Check if this file defines one of our classes
            for cls in class_list:
                pkg_path = cls.replace('.', '/') + '.java'
                if path.endswith(pkg_path):
                    # Found it! Extract module name
                    # Path looks like: modules/trishul-ai/src/main/java/...
                    module_name = path.split('/')[1]
                    package_name = '.'.join(cls.split('.')[:-1])
                    if module_name not in module_map:
                        module_map[module_name] = {'classes': [], 'package': package_name}
                    module_map[module_name]['classes'].append(cls)

for module_name, data in module_map.items():
    test_pkg = data['package']
    classes_to_test = sorted(data['classes'])
    
    test_dir = f"modules/{module_name}/src/test/java/{test_pkg.replace('.', '/')}"
    os.makedirs(test_dir, exist_ok=True)
    test_file_path = f"{test_dir}/ModelAccessorTest.java"
    
    with open(test_file_path, 'w') as f:
        f.write(f"package {test_pkg};\n\n")
        f.write("import io.trishul.test.util.PojoTestUtil;\n")
        f.write("import org.junit.jupiter.api.Test;\n\n")
        for cls in classes_to_test:
            f.write(f"import {cls};\n")
        
        f.write("\nclass ModelAccessorTest {\n")
        f.write("  @Test\n")
        f.write("  void testAccessors() throws Exception {\n")
        for cls in classes_to_test:
            simple_name = cls.split('.')[-1]
            f.write(f"    PojoTestUtil.assertAccessors({simple_name}.class);\n")
        f.write("  }\n")
        f.write("}\n")
        print(f"Generated {test_file_path}")

