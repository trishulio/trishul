package io.trishul.object.store.file.service.decorator;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.trishul.model.base.dto.BaseDto;
import io.trishul.object.store.file.decorator.EntityDecorator;
import io.trishul.object.store.file.model.accessor.DecoratedIaasObjectStoreFileAccessor;

public class DtoDecorator<T extends DecoratedIaasObjectStoreFileAccessor<? extends BaseDto>>
    implements EntityDecorator<T> {
  private static final Logger logger = LoggerFactory.getLogger(DtoDecorator.class);

  private final TemporaryImageSrcDecorator imageSrcDecorator;

  public DtoDecorator(TemporaryImageSrcDecorator imageSrcDecorator) {
    this.imageSrcDecorator = imageSrcDecorator;
  }

  @Override
  public <R extends T> void decorate(List<R> entities) {
    this.imageSrcDecorator.decorate(entities);
  }

}
