package io.trishul.user.service.user.service.controller;

import io.trishul.object.store.file.decorator.EntityDecorator;
import io.trishul.object.store.file.service.decorator.TemporaryImageSrcDecorator;
import io.trishul.user.model.UserDto;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserDtoDecorator implements EntityDecorator<UserDto> {
  @SuppressWarnings("unused")
  private static final Logger log = LoggerFactory.getLogger(UserDtoDecorator.class);

  private TemporaryImageSrcDecorator imageSrcDecorator;

  public UserDtoDecorator(TemporaryImageSrcDecorator imageSrcDecorator) {
    this.imageSrcDecorator = imageSrcDecorator;
  }

  @Override
  public <R extends UserDto> void decorate(List<R> entities) {
    this.imageSrcDecorator.decorate(entities);
  }
}
