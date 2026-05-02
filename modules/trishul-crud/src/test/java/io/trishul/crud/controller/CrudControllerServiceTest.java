package io.trishul.crud.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import io.trishul.base.types.base.pojo.Identified;
import io.trishul.crud.controller.filter.AttributeFilter;
import io.trishul.crud.service.CrudService;
import io.trishul.model.base.dto.BaseDto;
import io.trishul.model.base.exception.EntityNotFoundException;
import io.trishul.model.base.mapper.BaseMapper;
import io.trishul.object.store.file.decorator.EntityDecorator;
import io.trishul.repo.jpa.repository.model.dto.PageDto;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

@SuppressWarnings("unchecked")
class CrudControllerServiceTest {
  private CrudControllerService<Long, TestEntity, TestEntity, TestEntity, TestDto, TestDto, TestDto> controllerService;
  private AttributeFilter mFilter;
  private BaseMapper<TestEntity, TestDto, TestDto, TestDto> mMapper;
  private CrudService<Long, TestEntity, TestEntity, TestEntity, ?> mService;
  private EntityDecorator<TestDto> mDecorator;

  static class TestEntity implements Identified<Long> {
    private Long id;
    private String value;

    public TestEntity() {}

    public TestEntity(Long id, String value) {
      this.id = id;
      this.value = value;
    }

    @Override
    public Long getId() {
      return id;
    }

    public TestEntity setId(Long id) {
      this.id = id;
      return this;
    }

    public String getValue() {
      return value;
    }

    public void setValue(String value) {
      this.value = value;
    }
  }

  static class TestDto extends BaseDto {
    private Long id;
    private String value;

    public TestDto() {}

    public TestDto(Long id, String value) {
      this.id = id;
      this.value = value;
    }

    public Long getId() {
      return id;
    }

    public void setId(Long id) {
      this.id = id;
    }

    public String getValue() {
      return value;
    }

    public void setValue(String value) {
      this.value = value;
    }
  }

  @BeforeEach
  void init() {
    mFilter = mock(AttributeFilter.class);
    mMapper = mock(BaseMapper.class);
    mService = mock(CrudService.class);
    mDecorator = mock(EntityDecorator.class);

    controllerService
        = new CrudControllerService<>(mFilter, mMapper, mService, "TestEntity", mDecorator);
  }

  @Test
  void testConstructorWithoutDecorator_CreatesInstance() {
    CrudControllerService<Long, TestEntity, TestEntity, TestEntity, TestDto, TestDto, TestDto> service
        = new CrudControllerService<>(mFilter, mMapper, mService, "TestEntity");
    assertNotNull(service);
  }

  @Test
  void testGetAll_ReturnsPageDto() {
    TestEntity entity = new TestEntity(1L, "value");
    TestDto dto = new TestDto(1L, "value");
    Page<TestEntity> page = new PageImpl<>(List.of(entity));

    when(mMapper.toDto(entity)).thenReturn(dto);

    PageDto<TestDto> result = controllerService.getAll(page, null);

    assertNotNull(result);
    assertEquals(1, result.getContent().size());
    assertEquals(1L, result.getTotalElements());
    assertEquals(1, result.getTotalPages());
    verify(mDecorator).decorate(any());
  }

  @Test
  void testGetAll_AppliesFilter_WhenAttributesProvided() {
    TestEntity entity = new TestEntity(1L, "value");
    TestDto dto = new TestDto(1L, "value");
    Page<TestEntity> page = new PageImpl<>(List.of(entity));
    Set<String> attributes = Set.of("id");

    when(mMapper.toDto(entity)).thenReturn(dto);

    controllerService.getAll(page, attributes);

    verify(mFilter).retain(dto, attributes);
  }

  @Test
  void testGet_ReturnsDto_WhenEntityExists() {
    TestEntity entity = new TestEntity(1L, "value");
    TestDto dto = new TestDto(1L, "value");

    when(mService.get(1L)).thenReturn(entity);
    when(mMapper.toDto(entity)).thenReturn(dto);

    TestDto result = controllerService.get(1L, null);

    assertNotNull(result);
    assertEquals(1L, result.getId());
    verify(mDecorator).decorate(List.of(dto));
  }

  @Test
  void testGet_ThrowsEntityNotFoundException_WhenEntityDoesNotExist() {
    when(mService.get(1L)).thenReturn(null);

    assertThrows(EntityNotFoundException.class, () -> controllerService.get(1L, null));
  }

  @Test
  void testAdd_ReturnsAddedDtos() {
    TestDto addDto = new TestDto(null, "value");
    TestEntity addEntity = new TestEntity(null, "value");
    TestEntity addedEntity = new TestEntity(1L, "value");
    TestDto resultDto = new TestDto(1L, "value");

    when(mMapper.fromAddDto(addDto)).thenReturn(addEntity);
    when(mService.add(any())).thenReturn(List.of(addedEntity));
    when(mMapper.toDto(addedEntity)).thenReturn(resultDto);

    List<TestDto> result = controllerService.add(List.of(addDto));

    assertNotNull(result);
    assertEquals(1, result.size());
    assertEquals(1L, result.get(0).getId());
    verify(mDecorator).decorate(result);
  }

  @Test
  void testPut_ReturnsUpdatedDtos() {
    TestDto updateDto = new TestDto(1L, "updated");
    TestEntity updateEntity = new TestEntity(1L, "updated");
    TestDto resultDto = new TestDto(1L, "updated");

    when(mMapper.fromUpdateDto(updateDto)).thenReturn(updateEntity);
    when(mService.put(any())).thenReturn(List.of(updateEntity));
    when(mMapper.toDto(updateEntity)).thenReturn(resultDto);

    List<TestDto> result = controllerService.put(List.of(updateDto));

    assertNotNull(result);
    assertEquals(1, result.size());
    verify(mDecorator).decorate(result);
  }

  @Test
  void testPatch_ReturnsPatchedDtos() {
    TestDto patchDto = new TestDto(1L, "patched");
    TestEntity patchEntity = new TestEntity(1L, "patched");
    TestDto resultDto = new TestDto(1L, "patched");

    when(mMapper.fromUpdateDto(patchDto)).thenReturn(patchEntity);
    when(mService.patch(any())).thenReturn(List.of(patchEntity));
    when(mMapper.toDto(patchEntity)).thenReturn(resultDto);

    List<TestDto> result = controllerService.patch(List.of(patchDto));

    assertNotNull(result);
    assertEquals(1, result.size());
    verify(mDecorator).decorate(result);
  }

  @Test
  void testDelete_ReturnsDeleteCount() {
    when(mService.delete(Set.of(1L, 2L))).thenReturn(2L);

    long result = controllerService.delete(Set.of(1L, 2L));

    assertEquals(2L, result);
  }
}
