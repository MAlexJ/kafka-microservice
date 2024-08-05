package com.malex.filteringservice.service.filter;

import com.malex.filteringservice.mapper.ObjectMapper;
import com.malex.filteringservice.model.request.FilterRequest;
import com.malex.filteringservice.model.response.FilterResponse;
import com.malex.filteringservice.service.cache.FilterCacheService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class FilterRestService {

  private final ObjectMapper mapper;
  private final FilterCacheService cacheService;

  public Flux<FilterResponse> findAll() {
    log.info("Find all filters");
    return cacheService.findAll().map(mapper::entityToDto);
  }

  public Mono<FilterResponse> save(FilterRequest request) {
    log.info("Save new filter - {}", request);
    return Mono.fromSupplier(() -> mapper.dtoToEntity(request))
        .flatMap(cacheService::save)
        .map(mapper::entityToDto);
  }

  /** Inactive filter by id */
  public Mono<Void> deleteById(String id) {
    log.info("Delete filter by id - {}", id);
    return cacheService.deleteById(id);
  }
}
