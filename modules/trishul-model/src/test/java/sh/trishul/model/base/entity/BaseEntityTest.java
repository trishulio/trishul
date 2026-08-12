package sh.trishul.model.base.entity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class BaseEntityTest {

  @Test
  public void testBaseEntity() {
    ConcreteBaseEntity entity = new ConcreteBaseEntity();
    Assertions.assertNotNull(entity);
    Assertions.assertEquals(2925307725883732374L, ConcreteBaseEntity.serialVersionUID);
  }

  private static class ConcreteBaseEntity extends BaseEntity {
    private static final long serialVersionUID = 2925307725883732374L;
  }
}
