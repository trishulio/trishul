package io.trishul.model.base.mapper;

import java.util.IdentityHashMap;
import java.util.Map;
import org.mapstruct.BeforeMapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.TargetType;

/*
 * Used by mapstruct to prevent infinite loops due to cyclical references
 * https://github.com/mapstruct/mapstruct-examples/tree/master/mapstruct-mapping-with-cycles
 * https://github.com/mapstruct/mapstruct/issues/469
 */
public class CycleAvoidingMappingContext {
  private final Map<Object, Object> knownInstances = new IdentityHashMap<>();

  @SuppressWarnings("unchecked")
  @BeforeMapping
  public <T> T getMappedInstance(Object source, @TargetType Class<T> targetType) {
    return (T) knownInstances.get(source);
  }

  @BeforeMapping
  public void storeMappedInstance(Object source, @MappingTarget Object target) {
    knownInstances.put(source, target);
  }
}
