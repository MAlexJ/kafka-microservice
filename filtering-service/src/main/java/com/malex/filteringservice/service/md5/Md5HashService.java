package com.malex.filteringservice.service.md5;

import com.malex.filteringservice.model.entity.Md5HashEntity;
import com.malex.filteringservice.model.event.RssItem;
import com.malex.filteringservice.repository.Md5HashRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class Md5HashService {

  private final Md5HashRepository repository;

  public Flux<Boolean> isNotExistItemByMd5Hash(RssItem item) {
    var md5Hash = item.md5Hash();
    return repository
        .findByMd5Hash(md5Hash)
        .map(r -> false)
        .doOnNext(r -> log.info("is exist md5 hash - {} in db", md5Hash))
        .defaultIfEmpty(true);
  }

  public Mono<RssItem> saveItemMd5Hash(RssItem item) {
    var md5Hash = item.md5Hash();
    var entity = buildEntity(md5Hash);
    return repository.save(entity).map(r -> item);
  }

  private Md5HashEntity buildEntity(String md5Hash) {
    var entity = new Md5HashEntity();
    entity.setMd5Hash(md5Hash);
    return entity;
  }
}
