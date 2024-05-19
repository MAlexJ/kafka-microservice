package com.malex.filteringservice.service.storage;

import com.malex.filteringservice.model.entity.FilterEntity;
import com.malex.filteringservice.repository.FilterRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class FilterStorageService {

  private final FilterRepository repository;

  public Flux<FilterEntity> findAll() {
    return repository.findAll();
  }

  public List<FilterEntity> findFilterList() {
    return repository.findAll().toStream().toList();
  }

  public Mono<FilterEntity> save(FilterEntity entity) {
    return repository.save(entity);
  }

  /** Inactive filter by id */
  public Mono<Void> deleteById(String id) {
    return repository.deleteById(id);
  }
}
