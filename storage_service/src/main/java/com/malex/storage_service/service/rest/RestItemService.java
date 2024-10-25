package com.malex.storage_service.service.rest;

import com.malex.storage_service.mapper.ObjectMapper;
import com.malex.storage_service.model.request.ItemRequest;
import com.malex.storage_service.model.response.ItemResponse;
import com.malex.storage_service.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class RestItemService {

  private final ObjectMapper mapper;
  private final ItemRepository repository;

  public Flux<ItemResponse> findAll() {
    return repository.findAll().map(mapper::entityToResponse);
  }

  @Transactional
  public Mono<ItemResponse> create(ItemRequest request) {
    return Mono.just(mapper.requestToActiveEntity(request))
        .flatMap(repository::save)
        .map(mapper::entityToResponse);
  }
}
