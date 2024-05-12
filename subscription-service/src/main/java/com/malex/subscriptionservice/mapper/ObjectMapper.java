package com.malex.subscriptionservice.mapper;

import com.malex.subscriptionservice.model.Subscription;
import com.malex.subscriptionservice.model.entity.SubscriptionEntity;
import com.malex.subscriptionservice.model.request.SubscriptionRequest;
import com.malex.subscriptionservice.model.response.SubscriptionResponse;
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
  Subscription entityToModel(SubscriptionEntity entity);
}
