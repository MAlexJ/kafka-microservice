package com.malex.templateresolverservice.repository;

import com.malex.templateresolverservice.model.entity.TemplateEntity;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.Update;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface TemplateResolverRepository extends ReactiveCrudRepository<TemplateEntity, String> {

  @Query("{'id': ?0 }")
  @Update(update = "{ $set: { template : ?1 }}")
  Mono<Long> updateMessageTemplateEntityBy(String id, String template);
}
