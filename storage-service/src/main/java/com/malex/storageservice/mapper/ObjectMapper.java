package com.malex.storageservice.mapper;

import com.malex.storageservice.model.entity.ItemEntity;
import com.malex.storageservice.model.event.ItemEvent;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/** MapStruct mapper: */
@Mapper(componentModel = "spring")
public interface ObjectMapper {

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "active", expression = "java(true)")
  ItemEntity eventToActiveEntity(ItemEvent event);
}
