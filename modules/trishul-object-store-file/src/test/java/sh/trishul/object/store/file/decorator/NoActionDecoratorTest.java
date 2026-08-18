package sh.trishul.object.store.file.decorator;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class NoActionDecoratorTest {
  private NoActionDecorator<Object> decorator;

  @BeforeEach
  public void setUp() {
    decorator = new NoActionDecorator<>();
  }

  @Test
  public void testDecorate_DoesNothing() {
    assertDoesNotThrow(() -> decorator.decorate(List.of(new Object())));
  }

  @Test
  public void testDecorate_DoesNothing_WhenListIsNull() {
    assertDoesNotThrow(() -> decorator.decorate(null));
  }
}
