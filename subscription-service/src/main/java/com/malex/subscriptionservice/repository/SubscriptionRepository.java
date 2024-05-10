package com.malex.subscriptionservice.repository;

import com.malex.subscriptionservice.model.entity.SubscriptionEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubscriptionRepository
    extends ReactiveCrudRepository<SubscriptionEntity, String> {}
