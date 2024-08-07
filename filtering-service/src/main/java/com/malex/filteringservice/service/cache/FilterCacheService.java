package com.malex.filteringservice.service.cache;

import com.malex.filteringservice.model.entity.FilterEntity;
import com.malex.filteringservice.repository.FilterRepository;
import java.time.Duration;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class FilterCacheService {

  @Value("${spring.redis.ttl_in_minutes:15}")
  public Long ttlInMinutes;

  private final FilterRepository repository;

  private final ReactiveRedisTemplate<String, FilterEntity> template;

  public Flux<FilterEntity> findAll() {
    return template
        .keys("filter_entities:*")
        // Fetching cached filters
        .flatMap(key -> template.opsForValue().get(key))
        .doOnNext(filter -> log.info("Filter entity from cache - {}", filter))
        // If cache is empty, fetch the database for filters
        .switchIfEmpty(
            repository
                .findAll()
                // Persisting the fetched filters in the cache
                .flatMap(
                    filterEntity ->
                        template
                            .opsForValue()
                            .set(
                                "filter_entities:" + filterEntity.getId(),
                                filterEntity,
                                Duration.ofMinutes(ttlInMinutes)))
                // Fetching the filters from the updated cache
                .thenMany(
                    template
                        .keys("filter_entities:*")
                        .flatMap(key -> template.opsForValue().get(key))));
  }

  public Mono<FilterEntity> save(FilterEntity entity) {
    return repository.save(entity);
  }

  /** Inactive filter by id */
  public Mono<Void> deleteById(String id) {
    return repository.deleteById(id);
  }
}
