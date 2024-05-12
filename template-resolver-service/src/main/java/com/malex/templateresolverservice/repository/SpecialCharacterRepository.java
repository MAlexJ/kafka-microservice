package com.malex.templateresolverservice.repository;

import com.malex.templateresolverservice.model.entity.SpecialCharacterEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpecialCharacterRepository
    extends ReactiveCrudRepository<SpecialCharacterEntity, String> {}
