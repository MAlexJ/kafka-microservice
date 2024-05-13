package com.malex.filteringservice.repository;

import com.malex.filteringservice.model.entity.FilterEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FilterRepository extends ReactiveCrudRepository<FilterEntity, String> {}
