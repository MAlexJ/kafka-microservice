package com.malex.filteringservice.service.storage;

import com.malex.filteringservice.model.entity.Md5HashEntity;
import com.malex.filteringservice.repository.Md5HashRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
// @CacheConfig(cacheNames = MD5_CACHE_NAME)
public class Md5HashStorageService {

  private final Md5HashRepository repository;

  //  @Cacheable
  public Flux<Md5HashEntity> findByMd5Hash(String md5Hash) {
    return repository.findByMd5Hash(md5Hash);
  }

  //  @CacheEvict(allEntries = true, key = MD5_CACHE_NAME)
  public Mono<Md5HashEntity> save(Md5HashEntity entity) {
    return repository.save(entity);
  }
}
