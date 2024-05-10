package com.malex.subscriptionservice.repository;

import com.malex.subscriptionservice.model.entity.SubscriptionEntity;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.Update;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface SubscriptionRepository extends ReactiveCrudRepository<SubscriptionEntity, String> {

  @Query("{'id': ?0 }")
  @Update(update = "{ $set: { isActive : false }}")
  Mono<Long> unsubscribe(String id);

  @Query(value = "{ isActive : ?0 }")
  Flux<SubscriptionEntity> findActiveSubscription(boolean isActive);
}
