import re

with open('modules/trishul-data/src/test/java/io/trishul/data/datasource/manager/CachingDataSourceManagerTest.java', 'r') as f:
    content = f.read()

# Replace the field declaration
content = content.replace('private DataSourceBuilder mDataSourceBuilder;', '')
content = content.replace('when(mDataSourceBuilder.clear()).thenReturn(mDataSourceBuilder);', '')
content = content.replace('when(mDataSourceBuilder.url(any())).thenReturn(mDataSourceBuilder);', '')
content = content.replace('when(mDataSourceBuilder.schema(any())).thenReturn(mDataSourceBuilder);', '')
content = content.replace('when(mDataSourceBuilder.username(any())).thenReturn(mDataSourceBuilder);', '')
content = content.replace('when(mDataSourceBuilder.password(any())).thenReturn(mDataSourceBuilder);', '')
content = content.replace('when(mDataSourceBuilder.poolSize(any(Integer.class))).thenReturn(mDataSourceBuilder);', '')
content = content.replace('when(mDataSourceBuilder.autoCommit(any(Boolean.class))).thenReturn(mDataSourceBuilder);', '')

# We will wrap the test methods that use build() with mockConstruction
# Actually, wait. It's easier to just use a custom factory or just Mockito.mockConstruction.
