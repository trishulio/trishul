package io.trishul.user.service.user.service.controller;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import io.trishul.object.store.file.service.decorator.TemporaryImageSrcDecorator;
import io.trishul.user.model.UserDto;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Test;

public class UserDtoDecoratorTest {

  @Test
  public void testDecorate() {
    TemporaryImageSrcDecorator mockDecorator = mock(TemporaryImageSrcDecorator.class);
    UserDtoDecorator decorator = new UserDtoDecorator(mockDecorator);

    List<UserDto> list = Collections.singletonList(new UserDto());
    decorator.decorate(list);

    verify(mockDecorator).decorate(list);
  }
}
