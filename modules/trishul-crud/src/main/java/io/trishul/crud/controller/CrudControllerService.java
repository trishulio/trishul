package io.trishul.crud.controller;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;

import io.trishul.crud.controller.filter.AttributeFilter;
import io.trishul.crud.service.CrudService;
import io.trishul.model.base.dto.BaseDto;
import io.trishul.model.base.mapper.BaseMapper;
import static io.trishul.model.validator.Validator.assertion;
import io.trishul.object.decorator.EntityDecorator;
import io.trishul.object.decorator.NoActionDecorator;
import io.trishul.repo.jpa.repository.exception.EntityNotFoundException;
import io.trishul.repo.jpa.repository.model.dto.PageDto;
import io.trishul.repo.jpa.repository.model.pojo.Identified;

// import org.springframework.data.domain.Page;

public class CrudControllerService<
    ID,
    Entity extends UpdateEntity,
    AddEntity,
    UpdateEntity extends Identified<ID>,
    EntityDto extends BaseDto,
    AddDto extends BaseDto,
    UpdateDto extends BaseDto
> {
    private AttributeFilter filter;
    private BaseMapper<Entity, EntityDto, AddDto, UpdateDto> mapper;
    private CrudService<ID, Entity, AddEntity, UpdateEntity, ?> service;
    private String entityName;
    private EntityDecorator<EntityDto> decorator;

    public CrudControllerService(
        AttributeFilter filter,
        BaseMapper<Entity, EntityDto, AddDto, UpdateDto> mapper,
        CrudService<ID, Entity, AddEntity, UpdateEntity, ?> service,
        String entityName,
        EntityDecorator<EntityDto> decorator
    ) {
        this.filter = filter;
        this.mapper = mapper;
        this.service = service;
        this.entityName = entityName;
        this.decorator = decorator;
    }

    public CrudControllerService(
        AttributeFilter filter,
        BaseMapper<Entity, EntityDto, AddDto, UpdateDto> mapper,
        CrudService<ID, Entity, AddEntity, UpdateEntity, ?> service,
        String entityName
    ) {
        this(filter, mapper, service, entityName, new NoActionDecorator<>());
    }

    public PageDto<EntityDto> getAll(Page<Entity> entities, Set<String> attributes) {
        final List<EntityDto> content = entities.stream().map(i -> mapper.toDto(i)).toList();
        content.forEach(entity -> this.filter(entity, attributes));

        this.decorator.decorate(content);

        final PageDto<EntityDto> dto = new PageDto<>();
        dto.setContent(content);
        dto.setTotalElements(entities.getTotalElements());
        dto.setTotalPages(entities.getTotalPages());
        return dto;
    }

    public EntityDto get(ID id, Set<String> attributes) {
        Entity e = this.service.get(id);
        assertion(e != null, EntityNotFoundException.class, this.entityName, id.toString());

        EntityDto dto = mapper.toDto(e);
        this.filter(dto, attributes);

        this.decorator.decorate(List.of(dto));

        return dto;
    }

    public List<EntityDto> add(List<AddDto> addDtos) {
        @SuppressWarnings("unchecked")
        List<AddEntity> additions = (List<AddEntity>) addDtos.stream().map(dto -> mapper.fromAddDto(dto)).collect(Collectors.toList());
        List<Entity> added = this.service.add(additions);

        List<EntityDto> dtos = added.stream().map(entity -> mapper.toDto(entity)).toList();

        this.decorator.decorate(dtos);

        return dtos;
    }

    public List<EntityDto> put(List<UpdateDto> updateDtos) {
        List<UpdateEntity> updates = updateDtos.stream().map(dto -> mapper.fromUpdateDto(dto)).collect(Collectors.toList());
        List<Entity> updated = this.service.put(updates);

        List<EntityDto> dtos = updated.stream().map(entity -> mapper.toDto(entity)).toList();

        this.decorator.decorate(dtos);

        return dtos;
    }

    public List<EntityDto> patch(List<UpdateDto> updateDtos) {
        List<UpdateEntity> updates = updateDtos.stream().map(dto -> mapper.fromUpdateDto(dto)).collect(Collectors.toList());
        List<Entity> patched = this.service.patch(updates);

        List<EntityDto> dtos = patched.stream().map(entity -> mapper.toDto(entity)).toList();

        this.decorator.decorate(dtos);

        return dtos;
    }

    public long delete(Set<ID> ids) {
        return this.service.delete(ids);
    }

    private void filter(BaseDto dto, Set<String> retainAttr) {
        if (retainAttr != null && !retainAttr.isEmpty()) {
            this.filter.retain(dto, retainAttr);
        }
    }
}
