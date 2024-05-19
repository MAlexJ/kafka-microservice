package com.malex.filteringservice.service.storage;

import com.malex.filteringservice.model.entity.FilterEntity;
import com.malex.filteringservice.repository.FilterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
// @CacheConfig(cacheNames = FILTERS_CACHE_NAME)
public class FilterStorageService {

  private final FilterRepository repository;

  // @Cacheable
  public Flux<FilterEntity> findAll() {
    return repository.findAll();
  }

  //  @CacheEvict(allEntries = true, key = FILTERS_CACHE_NAME)
  public Mono<FilterEntity> save(FilterEntity entity) {
    return repository.save(entity);
  }

  /** Inactive filter by id */
  //  @CacheEvict(allEntries = true, key = FILTERS_CACHE_NAME)
  public Mono<Void> deleteById(String id) {
    return repository.deleteById(id);
  }
}
