package com.malex.filteringservice.service.md5;

import com.malex.filteringservice.model.entity.Md5HashEntity;
import com.malex.filteringservice.model.event.ItemEvent;
import com.malex.filteringservice.service.storage.Md5HashStorageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class Md5HashService {

  private final Md5HashStorageService service;

  public Flux<Boolean> isNotExistItemByMd5Hash(ItemEvent item) {
    var md5Hash = item.md5Hash();
    return service
        .findByMd5Hash(md5Hash)
        .map(r -> false)
        .doOnNext(r -> log.info("Skip sending event by md5 hash - {}", md5Hash))
        .defaultIfEmpty(true);
  }

  public Mono<ItemEvent> saveItemMd5Hash(ItemEvent item) {
    var md5Hash = item.md5Hash();
    var entity = buildEntity(md5Hash);
    log.info("Save item Md5Hash - {}", entity);
    return service.save(entity).map(r -> item);
  }

  private Md5HashEntity buildEntity(String md5Hash) {
    var entity = new Md5HashEntity();
    entity.setMd5Hash(md5Hash);
    return entity;
  }
}
