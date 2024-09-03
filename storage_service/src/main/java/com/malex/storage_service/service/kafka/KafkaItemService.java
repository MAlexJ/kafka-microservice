package com.malex.storage_service.service.kafka;

import com.malex.storage_service.mapper.ObjectMapper;
import com.malex.storage_service.model.entity.ItemEntity;
import com.malex.storage_service.model.event.ItemEvent;
import com.malex.storage_service.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaItemService {

  private final ObjectMapper mapper;
  private final ItemRepository repository;

  @Transactional
  public Mono<ItemEntity> save(ItemEvent event) {
    return Mono.just(mapper.eventToActiveEntity(event))
        .flatMap(repository::save)
        .doOnNext(ent -> log.debug("Item event saved to db - {}", ent));
  }
}
