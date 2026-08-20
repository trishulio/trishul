package sh.trishul.user.service.user.service.controller;

import java.util.List;
import sh.trishul.object.store.file.decorator.EntityDecorator;
import sh.trishul.object.store.file.service.decorator.TemporaryImageSrcDecorator;
import sh.trishul.user.model.UserDto;

public class UserDtoDecorator implements EntityDecorator<UserDto> {
  private TemporaryImageSrcDecorator imageSrcDecorator;

  public UserDtoDecorator(TemporaryImageSrcDecorator imageSrcDecorator) {
    this.imageSrcDecorator = imageSrcDecorator;
  }

  @Override
  public <R extends UserDto> void decorate(List<R> entities) {
    this.imageSrcDecorator.decorate(entities);
  }
}
