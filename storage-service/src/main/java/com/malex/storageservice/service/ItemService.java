package com.malex.storageservice.service;

import com.malex.storageservice.mapper.ObjectMapper;
import com.malex.storageservice.model.entity.ItemEntity;
import com.malex.storageservice.model.event.ItemEvent;
import com.malex.storageservice.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class ItemService {

  private final ItemRepository repository;
  private final ObjectMapper mapper;

  @Transactional
  public Mono<ItemEntity> save(ItemEvent event) {
    return Mono.just(mapper.eventToActiveEntity(event))
        .flatMap(repository::save)
        .doOnNext(ent -> log.debug("Item event saved to db - {}", ent));
  }
}
