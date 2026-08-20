package sh.trishul.object.store.file.service.decorator;

import java.util.List;
import sh.trishul.model.base.dto.BaseDto;
import sh.trishul.object.store.file.decorator.EntityDecorator;
import sh.trishul.object.store.file.model.accessor.DecoratedIaasObjectStoreFileAccessor;

public class DtoDecorator<T extends DecoratedIaasObjectStoreFileAccessor<? extends BaseDto>>
    implements EntityDecorator<T> {
  private final TemporaryImageSrcDecorator imageSrcDecorator;

  public DtoDecorator(TemporaryImageSrcDecorator imageSrcDecorator) {
    this.imageSrcDecorator = imageSrcDecorator;
  }

  @Override
  public <R extends T> void decorate(List<R> entities) {
    this.imageSrcDecorator.decorate(entities);
  }

}
