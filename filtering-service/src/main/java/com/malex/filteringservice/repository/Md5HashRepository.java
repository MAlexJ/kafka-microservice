package com.malex.filteringservice.repository;

import com.malex.filteringservice.model.entity.Md5HashEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface Md5HashRepository extends ReactiveCrudRepository<Md5HashEntity, String> {

  Flux<Md5HashEntity> findByMd5Hash(String md5Hash);
}
