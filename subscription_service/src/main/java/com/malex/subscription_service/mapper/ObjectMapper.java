package com.malex.subscription_service.mapper;

import com.malex.subscription_service.model.SubscriptionEvent;
import com.malex.subscription_service.model.entity.SubscriptionEntity;
import com.malex.subscription_service.model.request.SubscriptionRequest;
import com.malex.subscription_service.model.response.SubscriptionResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/** MapStruct mapper: */
@Mapper(componentModel = "spring")
public interface ObjectMapper {

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "created", ignore = true)
  @Mapping(source = "isActive", target = "active")
  SubscriptionEntity requestToEntity(SubscriptionRequest dto);

  @Mapping(source = "active", target = "isActive")
  SubscriptionResponse entityToResponse(SubscriptionEntity entity);

  @Mapping(source = "active", target = "isActive")
  SubscriptionEvent entityToModel(SubscriptionEntity entity);
}
