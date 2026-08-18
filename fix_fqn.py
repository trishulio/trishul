import re

def fix_file(filepath, replacements, imports_to_add):
    with open(filepath, 'r') as f:
        content = f.read()

    # Apply replacements
    for old, new in replacements:
        content = content.replace(old, new)

    # Add imports
    lines = content.split('\n')
    
    # Find last import
    last_import_idx = -1
    for i, line in enumerate(lines):
        if line.startswith('import '):
            last_import_idx = i
            
    if last_import_idx != -1:
        for imp in imports_to_add:
            import_statement = f"import {imp};"
            if import_statement not in content:
                lines.insert(last_import_idx + 1, import_statement)
                last_import_idx += 1
    
    content = '\n'.join(lines)
    with open(filepath, 'w') as f:
        f.write(content)

# 1 & 2
fix_file('modules/trishul-money/src/test/java/io/trishul/money/amount/model/AmountTest.java', 
         [('org.joda.money.Money.parse', 'Money.parse')], 
         ['org.joda.money.Money'])
fix_file('modules/trishul-money/src/test/java/io/trishul/money/tax/amount/TaxAmountTest.java', 
         [('org.joda.money.Money.parse', 'Money.parse')], 
         ['org.joda.money.Money'])

# 3
fix_file('modules/trishul-model/src/main/java/io/trishul/model/logger/Slf4jLoggerWrapper.java',
         [('org.slf4j.Marker', 'Marker'), 
          ('java.lang.String', 'String'), 
          ('java.lang.Throwable', 'Throwable'), 
          ('java.lang.Object', 'Object')],
         ['org.slf4j.Marker']) # String, Throwable, Object are in java.lang so they don't need imports

# 4
fix_file('modules/trishul-data/src/main/java/io/trishul/data/datasource/configuration/model/LazyTenantDataSourceConfiguration.java',
         [('java.util.Objects.equals', 'Objects.equals'),
          ('java.util.Objects.hash', 'Objects.hash')],
         ['java.util.Objects'])

# 5
fix_file('modules/trishul-object-store/src/test/java/io/trishul/object/store/configuration/access/model/IaasObjectStoreAccessConfigTest.java',
         [('org.mockito.Mockito.mock', 'mock')],
         ['static org.mockito.Mockito.mock'])

# 6
fix_file('modules/trishul-object-store/src/test/java/io/trishul/object/store/configuration/cors/model/IaasObjectStoreCorsConfigurationTest.java',
         [('org.mockito.Mockito.mock', 'mock')],
         ['static org.mockito.Mockito.mock'])

# 7 & 8
fix_file('modules/trishul-ai/src/test/java/io/trishul/ai/agent/model/UpdateAiAgentConfigDtoTest.java',
         [('java.util.Set.of', 'Set.of')],
         ['java.util.Set'])
fix_file('modules/trishul-ai/src/test/java/io/trishul/ai/agent/model/AddAiAgentConfigDtoTest.java',
         [('java.util.Set.of', 'Set.of')],
         ['java.util.Set'])

# 9
fix_file('modules/trishul-user/src/test/java/io/trishul/user/model/UserTest.java',
         [('org.apache.commons.lang3.reflect.FieldUtils.writeField', 'FieldUtils.writeField')],
         ['org.apache.commons.lang3.reflect.FieldUtils'])

print("Files fixed successfully.")
