package com.malex.filteringservice.repository;

import com.malex.filteringservice.model.entity.FilterEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface FilterRepository extends ReactiveCrudRepository<FilterEntity, String> {

  /*
   * Pageable: https://www.youtube.com/watch?v=r89aJJzISj8&list=PLAZHf0fSXoc_M4i16j_SuNooSbNa8M9_-&index=15
   */
  Flux<FilterEntity> findAllBy(Pageable pageable);
}
