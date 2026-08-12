import re

with open("modules/trishul-data/src/test/java/sh/trishul/data/datasource/manager/CachingDataSourceManagerTest.java", "r") as f:
    content = f.read()

# Add import for mockConstruction and MockedConstruction
if 'import org.mockito.MockedConstruction;' not in content:
    content = content.replace('import org.junit.jupiter.api.Test;', 'import org.junit.jupiter.api.Test;\nimport org.mockito.MockedConstruction;\nimport static org.mockito.Mockito.mockConstruction;\nimport sh.trishul.data.datasource.builder.HikariDataSourceBuilder;')

# Define the mock setup block
mock_setup = """try (MockedConstruction<HikariDataSourceBuilder> mocked = mockConstruction(HikariDataSourceBuilder.class, (mock, context) -> {
      when(mock.clear()).thenReturn(mock);
      when(mock.url(any())).thenReturn(mock);
      when(mock.schema(any())).thenReturn(mock);
      when(mock.username(any())).thenReturn(mock);
      when(mock.password(any())).thenReturn(mock);
      when(mock.poolSize(any(Integer.class))).thenReturn(mock);
      when(mock.autoCommit(any(Boolean.class))).thenReturn(mock);
      when(mock.build()).thenReturn(mBuiltDs);
    })) {"""

# Replace old builder mock usage in tests with MockedConstruction
# 1. testGetDataSource_BuildsNewDataSource_WhenConfigSchemaDiffersFromAdminSchema
content = re.sub(
    r'(DataSource result = dataSourceManager\.getDataSource\(mDsConfig\);)',
    mock_setup + r'\n    \1',
    content
)

# Replace the closing brace of each modified test to close the try block as well
content = re.sub(
    r'(verify\(mDataSourceBuilder\)\.build\(\);\n  \})',
    r'\1\n  }',
    content
)

# Actually, doing this with regex is tricky and prone to error. Let's just rewrite the whole test class since it's only 346 lines.
