package com.malex.filteringservice.service.storage;

import com.malex.filteringservice.mapper.ObjectMapper;
import com.malex.filteringservice.model.entity.FilterEntity;
import com.malex.filteringservice.model.request.FilterRequest;
import com.malex.filteringservice.model.response.FilterResponse;
import com.malex.filteringservice.repository.FilterRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class FilterStorageService {


  private final ObjectMapper mapper;
  private final FilterRepository repository;

  public Flux<FilterResponse> findAll() {
    log.info("Find all filters");
    return repository.findAll().map(mapper::entityToDto);
  }

  public Flux<FilterEntity> findAllActiveFilters() {
    log.info("Find all filter entities");
    return repository.findAll();
  }

  public Mono<FilterResponse> save(FilterRequest request) {
    log.info("Save new filter - {}", request);
    return Mono.fromSupplier(() -> mapper.dtoToEntity(request))
        .flatMap(repository::save)
        .map(mapper::entityToDto);
  }

  /** Inactive filter by id */
  public Mono<Void> deleteById(String id) {
    log.info("Delete filter by id - {}", id);
    return repository.deleteById(id);
  }
}
