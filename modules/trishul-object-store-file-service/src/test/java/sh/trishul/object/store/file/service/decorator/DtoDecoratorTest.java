package sh.trishul.object.store.file.service.decorator;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.util.List;
import org.junit.jupiter.api.Test;

class DtoDecoratorTest {
  @Test
  void testDecorate_CallsTemporaryImageSrcDecorator() {
    TemporaryImageSrcDecorator mImageSrcDecorator = mock(TemporaryImageSrcDecorator.class);
    DtoDecorator<DummyDto> decorator = new DtoDecorator<>(mImageSrcDecorator);

    List<DummyDto> entities = List.of(new DummyDto(1L));
    decorator.decorate(entities);

    verify(mImageSrcDecorator).decorate(entities);
  }
}
