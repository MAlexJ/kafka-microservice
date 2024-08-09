package com.malex.filteringservice.mapper;

import com.malex.filteringservice.model.entity.FilterEntity;
import com.malex.filteringservice.model.request.FilterRequest;
import com.malex.filteringservice.model.response.FilterResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/** MapStruct mapper: */
@Mapper(componentModel = "spring")
public interface ObjectMapper {

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "created", ignore = true)
  FilterEntity dtoToEntity(FilterRequest dto);

  FilterResponse entityToDto(FilterEntity entity);
}
