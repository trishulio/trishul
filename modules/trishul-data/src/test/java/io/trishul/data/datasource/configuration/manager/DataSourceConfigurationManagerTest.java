package io.trishul.data.datasource.configuration.manager;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DataSourceConfigurationManagerTest {
  private DataSourceConfigurationManager manager;

  @BeforeEach
  void init() {
    manager = new DataSourceConfigurationManager();
  }

  @Test
  void testGetFqName_ReturnsNonNullValue() {
    UUID tenantId = UUID.fromString("12345678-1234-1234-1234-123456789abc");
    String result = manager.getFqName("prefix_", tenantId);
    assertNotNull(result);
  }

  @Test
  void testGetFqName_ReturnsSchemaPrefixConcatenatedWithTenantIdWithoutDashes() {
    UUID tenantId = UUID.fromString("12345678-1234-1234-1234-123456789abc");
    String result = manager.getFqName("tenant_", tenantId);
    assertEquals("tenant_12345678_1234_1234_1234_123456789abc", result);
  }

  @Test
  void testGetFqName_ReturnsCorrectFormat_WhenPrefixIsEmpty() {
    UUID tenantId = UUID.fromString("aaaaaaaa-bbbb-cccc-dddd-eeeeeeeeeeee");
    String result = manager.getFqName("", tenantId);
    assertEquals("aaaaaaaa_bbbb_cccc_dddd_eeeeeeeeeeee", result);
  }

  @Test
  void testGetFqName_ReturnsCorrectFormat_WithDifferentPrefix() {
    UUID tenantId = UUID.fromString("11111111-2222-3333-4444-555555555555");
    String result = manager.getFqName("schema_", tenantId);
    assertEquals("schema_11111111_2222_3333_4444_555555555555", result);
  }

  @Test
  void testGetFqName_ReplacesAllDashesWithUnderscores() {
    UUID tenantId = UUID.fromString("00000000-0000-0000-0000-000000000000");
    String result = manager.getFqName("test_", tenantId);
    // UUID has 4 dashes, all should be replaced with underscores
    assertEquals("test_00000000_0000_0000_0000_000000000000", result);
    assertEquals(-1, result.indexOf('-'));
  }
}
