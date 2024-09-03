package com.malex.storage_service.mapper;

import com.malex.storage_service.model.entity.ItemEntity;
import com.malex.storage_service.model.event.ItemEvent;
import com.malex.storage_service.model.request.ItemRequest;
import com.malex.storage_service.model.response.ItemResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/** MapStruct mapper: */
@Mapper(componentModel = "spring")
public interface ObjectMapper {

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "active", expression = "java(true)")
  ItemEntity eventToActiveEntity(ItemEvent event);

  @Mapping(target = "isActive", source = "active")
  ItemResponse entityToResponse(ItemEntity entity);

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "active", expression = "java(true)")
  ItemEntity requestToActiveEntity(ItemRequest request);
}
