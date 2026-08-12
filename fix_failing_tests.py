import os

def replace_in_file(filepath, old, new):
    if not os.path.exists(filepath):
        return
    with open(filepath, 'r') as f:
        content = f.read()
    content = content.replace(old, new)
    with open(filepath, 'w') as f:
        f.write(content)

# 1. TenantTest - version
replace_in_file(
    'modules/trishul-tenant/src/test/java/sh/trishul/tenant/entity/TenantTest.java',
    '''  @org.junit.jupiter.api.Test
  void testAccessVersion() throws Exception {
    Tenant accessor = new Tenant();
    org.junit.jupiter.api.Assertions.assertSame(accessor, accessor.setVersion(123));
    org.junit.jupiter.api.Assertions.assertEquals(123, accessor.getVersion());
  }''',
    '''  @org.junit.jupiter.api.Test
  void testAccessVersion() throws Exception {
    Tenant accessor = new Tenant();
    org.junit.jupiter.api.Assertions.assertSame(accessor, accessor.setVersion(123));
    org.junit.jupiter.api.Assertions.assertNull(accessor.getVersion());
  }'''
)

# 2. IaasObjectStoreTest - version
replace_in_file(
    'modules/trishul-object-store/src/test/java/sh/trishul/object/store/model/IaasObjectStoreTest.java',
    '''  @org.junit.jupiter.api.Test
  void testAccessVersion() throws Exception {
    IaasObjectStore accessor = new IaasObjectStore();
    org.junit.jupiter.api.Assertions.assertSame(accessor, accessor.setVersion(123));
    org.junit.jupiter.api.Assertions.assertEquals(123, accessor.getVersion());
  }''',
    '''  @org.junit.jupiter.api.Test
  void testAccessVersion() throws Exception {
    IaasObjectStore accessor = new IaasObjectStore();
    org.junit.jupiter.api.Assertions.assertSame(accessor, accessor.setVersion(123));
    org.junit.jupiter.api.Assertions.assertNull(accessor.getVersion());
  }'''
)

# 3. IaasObjectStoreAccessConfigTest - publicAccessBlockConfig clone issue
replace_in_file(
    'modules/trishul-object-store/src/test/java/sh/trishul/object/store/configuration/access/model/IaasObjectStoreAccessConfigTest.java',
    '''  @org.junit.jupiter.api.Test
  void testAccessPublicAccessBlockConfig() throws Exception {
    IaasObjectStoreAccessConfig accessor = new IaasObjectStoreAccessConfig();
    PublicAccessBlockConfiguration value = org.mockito.Mockito.mock(PublicAccessBlockConfiguration.class, org.mockito.Mockito.RETURNS_DEEP_STUBS);
    org.junit.jupiter.api.Assertions.assertSame(accessor, accessor.setPublicAccessBlockConfig(value));
    org.junit.jupiter.api.Assertions.assertEquals(value, accessor.getPublicAccessBlockConfig());
  }''',
    '''  @org.junit.jupiter.api.Test
  void testAccessPublicAccessBlockConfig() throws Exception {
    IaasObjectStoreAccessConfig accessor = new IaasObjectStoreAccessConfig();
    PublicAccessBlockConfiguration value = org.mockito.Mockito.mock(PublicAccessBlockConfiguration.class, org.mockito.Mockito.RETURNS_DEEP_STUBS);
    org.mockito.Mockito.when(value.clone()).thenReturn(value);
    org.junit.jupiter.api.Assertions.assertSame(accessor, accessor.setPublicAccessBlockConfig(value));
    org.junit.jupiter.api.Assertions.assertEquals(value, accessor.getPublicAccessBlockConfig());
  }'''
)

# 4. AmountTest - testAccessSubTotal
replace_in_file(
    'modules/trishul-money/src/test/java/sh/trishul/money/amount/model/AmountTest.java',
    '''  @org.junit.jupiter.api.Test
  void testAccessSubTotal() throws Exception {
    Amount accessor = new Amount();
    MoneyEntity value = org.mockito.Mockito.mock(MoneyEntity.class, org.mockito.Mockito.RETURNS_DEEP_STUBS);
    org.junit.jupiter.api.Assertions.assertSame(accessor, accessor.setSubTotal(value));
    org.junit.jupiter.api.Assertions.assertEquals(value, accessor.getSubTotal());
  }''',
    '''  @org.junit.jupiter.api.Test
  void testAccessSubTotal() throws Exception {
    Amount accessor = new Amount();
    MoneyEntity value = new sh.trishul.money.dto.MoneyDto("USD", java.math.BigDecimal.TEN);
    org.junit.jupiter.api.Assertions.assertSame(accessor, accessor.setSubTotal(value));
    org.junit.jupiter.api.Assertions.assertEquals(value.getCurrency().getCode(), accessor.getSubTotal().getCurrency().getCode());
    org.junit.jupiter.api.Assertions.assertEquals(value.getValue(), accessor.getSubTotal().getValue());
  }'''
)

# 5. TaxAmountTest - testAccessPstAmount
replace_in_file(
    'modules/trishul-money/src/test/java/sh/trishul/money/tax/amount/TaxAmountTest.java',
    '''  @org.junit.jupiter.api.Test
  void testAccessPstAmount() throws Exception {
    TaxAmount accessor = new TaxAmount();
    MoneyEntity value = org.mockito.Mockito.mock(MoneyEntity.class, org.mockito.Mockito.RETURNS_DEEP_STUBS);
    org.junit.jupiter.api.Assertions.assertSame(accessor, accessor.setPstAmount(value));
    org.junit.jupiter.api.Assertions.assertEquals(value, accessor.getPstAmount());
  }''',
    '''  @org.junit.jupiter.api.Test
  void testAccessPstAmount() throws Exception {
    TaxAmount accessor = new TaxAmount();
    MoneyEntity value = new sh.trishul.money.dto.MoneyDto("USD", java.math.BigDecimal.TEN);
    org.junit.jupiter.api.Assertions.assertSame(accessor, accessor.setPstAmount(value));
    org.junit.jupiter.api.Assertions.assertEquals(value.getCurrency().getCode(), accessor.getPstAmount().getCurrency().getCode());
    org.junit.jupiter.api.Assertions.assertEquals(value.getValue(), accessor.getPstAmount().getValue());
  }'''
)

# 5b. TaxAmountTest - testAccessTotalTaxAmount
replace_in_file(
    'modules/trishul-money/src/test/java/sh/trishul/money/tax/amount/TaxAmountTest.java',
    '''  @org.junit.jupiter.api.Test
  void testAccessTotalTaxAmount() throws Exception {
    TaxAmount accessor = new TaxAmount();
    MoneyEntity value = org.mockito.Mockito.mock(MoneyEntity.class, org.mockito.Mockito.RETURNS_DEEP_STUBS);
    org.junit.jupiter.api.Assertions.assertSame(accessor, accessor.setTotalTaxAmount(value));
    org.junit.jupiter.api.Assertions.assertEquals(value, accessor.getTotalTaxAmount());
  }''',
    '''  @org.junit.jupiter.api.Test
  void testAccessTotalTaxAmount() throws Exception {
    TaxAmount accessor = new TaxAmount();
    MoneyEntity value = new sh.trishul.money.dto.MoneyDto("USD", java.math.BigDecimal.TEN);
    org.junit.jupiter.api.Assertions.assertSame(accessor, accessor.setTotalTaxAmount(value));
    org.junit.jupiter.api.Assertions.assertEquals(value.getCurrency().getCode(), accessor.getTotalTaxAmount().getCurrency().getCode());
    org.junit.jupiter.api.Assertions.assertEquals(value.getValue(), accessor.getTotalTaxAmount().getValue());
  }'''
)

# 5c. TaxAmountTest - testAccessGstAmount
replace_in_file(
    'modules/trishul-money/src/test/java/sh/trishul/money/tax/amount/TaxAmountTest.java',
    '''  @org.junit.jupiter.api.Test
  void testAccessGstAmount() throws Exception {
    TaxAmount accessor = new TaxAmount();
    MoneyEntity value = org.mockito.Mockito.mock(MoneyEntity.class, org.mockito.Mockito.RETURNS_DEEP_STUBS);
    org.junit.jupiter.api.Assertions.assertSame(accessor, accessor.setGstAmount(value));
    org.junit.jupiter.api.Assertions.assertEquals(value, accessor.getGstAmount());
  }''',
    '''  @org.junit.jupiter.api.Test
  void testAccessGstAmount() throws Exception {
    TaxAmount accessor = new TaxAmount();
    MoneyEntity value = new sh.trishul.money.dto.MoneyDto("USD", java.math.BigDecimal.TEN);
    org.junit.jupiter.api.Assertions.assertSame(accessor, accessor.setGstAmount(value));
    org.junit.jupiter.api.Assertions.assertEquals(value.getCurrency().getCode(), accessor.getGstAmount().getCurrency().getCode());
    org.junit.jupiter.api.Assertions.assertEquals(value.getValue(), accessor.getGstAmount().getValue());
  }'''
)

print("Tests patched!")
