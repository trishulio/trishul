import os
from collections import defaultdict

# The list of classes from the grep output
classes = """
sh.trishul.ai.agent.model.AddAiAgentConfigDto
sh.trishul.ai.agent.model.AiAgentConfigDto
sh.trishul.ai.agent.model.AiAgentGuardrail
sh.trishul.ai.agent.model.AiAgentSkill
sh.trishul.ai.agent.model.AiAgentTool
sh.trishul.ai.agent.model.UpdateAiAgentConfigDto
sh.trishul.ai.chat.model.AddAiChatModelConfigDto
sh.trishul.ai.chat.model.AiChatModelConfigDto
sh.trishul.ai.chat.model.UpdateAiChatModelConfigDto
sh.trishul.ai.guardrail.model.AddAiGuardrailDto
sh.trishul.ai.guardrail.model.AiGuardrailDto
sh.trishul.ai.guardrail.model.UpdateAiGuardrailDto
sh.trishul.ai.memory.model.AddAiChatMemoryConfigDto
sh.trishul.ai.memory.model.AiChatMemoryConfigDto
sh.trishul.ai.memory.model.UpdateAiChatMemoryConfigDto
sh.trishul.ai.session.model.AddAiChatSessionDto
sh.trishul.ai.session.model.AiChatSessionDto
sh.trishul.ai.session.model.UpdateAiChatSessionDto
sh.trishul.ai.skill.model.AddAiSkillDto
sh.trishul.ai.skill.model.AiSkillDto
sh.trishul.ai.skill.model.UpdateAiSkillDto
sh.trishul.ai.speech.model.AddAiSpeechConfigDto
sh.trishul.ai.speech.model.AiSpeechConfigDto
sh.trishul.ai.speech.model.UpdateAiSpeechConfigDto
sh.trishul.ai.tool.model.AddAiToolDto
sh.trishul.ai.tool.model.AiToolDto
sh.trishul.ai.tool.model.UpdateAiToolDto
sh.trishul.data.datasource.configuration.model.MigrationConfiguration
sh.trishul.iaas.access.policy.model.IaasPolicy
sh.trishul.iaas.access.role.attachment.policy.IaasRolePolicyAttachment
sh.trishul.iaas.access.role.attachment.policy.IaasRolePolicyAttachmentId
sh.trishul.iaas.access.role.model.IaasRole
sh.trishul.iaas.auth.session.context.IaasAuthorization
sh.trishul.iaas.auth.session.context.IaasAuthorizationDto
sh.trishul.iaas.idp.tenant.model.IaasIdpTenant
sh.trishul.iaas.idp.tenant.model.TenantIaasAuthDeleteResult
sh.trishul.iaas.idp.tenant.model.TenantIaasAuthResources
sh.trishul.iaas.idp.tenant.model.TenantIaasIdpDeleteResult
sh.trishul.iaas.idp.tenant.model.TenantIaasIdpResources
sh.trishul.iaas.tenant.object.store.TenantIaasVfsDeleteResult
sh.trishul.iaas.tenant.resource.TenantIaasResources
sh.trishul.iaas.tenant.service.TenantIaasDeleteResult
sh.trishul.iaas.user.model.IaasUser
sh.trishul.iaas.user.model.IaasUserTenantMembership
sh.trishul.integration.communication.model.AddIntegrationCommunicationConfigDto
sh.trishul.integration.communication.model.IntegrationCommunicationConfig
sh.trishul.integration.communication.model.IntegrationCommunicationConfigDto
sh.trishul.integration.communication.model.UpdateIntegrationCommunicationConfigDto
sh.trishul.integration.model.AddIntegrationDto
sh.trishul.integration.model.Integration
sh.trishul.integration.model.IntegrationDto
sh.trishul.integration.model.UpdateIntegrationDto
sh.trishul.model.base.dto.DeleteResultDto
sh.trishul.model.base.pojo.DeleteResult
sh.trishul.money.amount.model.AmountDto
sh.trishul.money.dto.MoneyDto
sh.trishul.money.tax.amount.TaxAmount
sh.trishul.money.tax.amount.dto.TaxAmountDto
sh.trishul.money.tax.dto.TaxDto
sh.trishul.money.tax.rate.dto.TaxRateDto
sh.trishul.object.store.configuration.access.model.IaasObjectStoreAccessConfig
sh.trishul.object.store.configuration.cors.model.IaasObjectStoreCorsConfiguration
sh.trishul.object.store.model.IaasObjectStore
sh.trishul.object.store.file.model.IaasObjectStoreFile
sh.trishul.quantity.model.QuantityEntity
sh.trishul.quantity.model.dto.QuantityDto
sh.trishul.quantity.unit.UnitEntity
sh.trishul.quantity.unit.dto.UnitDto
sh.trishul.repo.jpa.repository.model.dto.PageDto
sh.trishul.tenant.dto.AddTenantDto
sh.trishul.tenant.dto.TenantDto
sh.trishul.tenant.dto.UpdateTenantDto
sh.trishul.tenant.entity.AdminTenant
sh.trishul.tenant.entity.Tenant
sh.trishul.user.model.AddUserDto
sh.trishul.user.model.UpdateUserDto
sh.trishul.user.model.User
sh.trishul.user.model.UserDto
sh.trishul.user.role.binding.model.UserRoleBinding
sh.trishul.user.salutation.model.UserSalutation
sh.trishul.user.salutation.model.UserSalutationDto
sh.trishul.user.status.UserStatus
sh.trishul.user.status.UserStatusDto
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
        f.write("import sh.trishul.test.util.PojoTestUtil;\n")
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

