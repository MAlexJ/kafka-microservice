package com.malex.storageservice.repository;

import com.malex.storageservice.model.entity.ItemEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends ReactiveCrudRepository<ItemEntity, Long> {}
