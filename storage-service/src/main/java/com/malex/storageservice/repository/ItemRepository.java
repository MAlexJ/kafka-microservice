package com.malex.storageservice.repository;

import com.malex.storageservice.model.entity.ItemEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface ItemRepository extends ReactiveCrudRepository<ItemEntity, Long> {

  /*
   * Pageable: https://www.youtube.com/watch?v=r89aJJzISj8&list=PLAZHf0fSXoc_M4i16j_SuNooSbNa8M9_-&index=15
   */
  Flux<ItemEntity> findAllBy(Pageable pageable);
}
