package com.malex.filteringservice.service.filter;

import com.malex.filteringservice.mapper.ObjectMapper;
import com.malex.filteringservice.model.entity.FilterEntity;
import com.malex.filteringservice.model.request.FilterRequest;
import com.malex.filteringservice.model.response.FilterResponse;
import com.malex.filteringservice.service.storage.FilterStorageService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class FilterService {

  private final ObjectMapper mapper;
  private final FilterStorageService service;

  public Flux<FilterResponse> findAll() {
    log.info("Find all filters");
    return service.findAll().map(mapper::entityToDto);
  }

  /** Test impl */
  public List<FilterEntity> findFilterList() {
    return service.findAll().toStream().toList();
  }

  public Mono<FilterResponse> save(FilterRequest request) {
    log.info("Save new filter - {}", request);
    return Mono.fromSupplier(() -> mapper.dtoToEntity(request))
        .flatMap(service::save)
        .map(mapper::entityToDto);
  }

  /** Inactive filter by id */
  public Mono<Void> deleteById(String id) {
    log.info("Delete filter by id - {}", id);
    return service.deleteById(id);
  }
}
