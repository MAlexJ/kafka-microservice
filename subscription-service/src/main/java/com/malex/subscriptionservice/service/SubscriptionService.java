package com.malex.subscriptionservice.service;

import com.malex.subscriptionservice.mapper.ObjectMapper;
import com.malex.subscriptionservice.model.SubscriptionEvent;
import com.malex.subscriptionservice.model.request.SubscriptionRequest;
import com.malex.subscriptionservice.model.response.SubscriptionResponse;
import com.malex.subscriptionservice.repository.SubscriptionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class SubscriptionService {

  private final ObjectMapper mapper;
  private final SubscriptionRepository repository;

  public Mono<SubscriptionResponse> subscribe(SubscriptionRequest request) {
    return Mono.just(mapper.requestToEntity(request))
        .flatMap(repository::save)
        .map(mapper::entityToResponse);
  }

  public Flux<SubscriptionResponse> findAllSubscriptions() {
    return repository.findAll().map(mapper::entityToResponse);
  }

  public Flux<SubscriptionEvent> findAllActiveSubscriptions() {
    return repository.findActiveSubscription(true).map(mapper::entityToModel);
  }

  public Mono<Long> unsubscribe(String id) {
    return repository
        .unsubscribe(id)
        .doOnNext(
            numberOfTopicsUpdated -> {
              switch (numberOfTopicsUpdated.intValue()) {
                case 0 -> log.warn("No record was found by id - {}", id);
                case 1 -> log.info("Unsubscribe from RSS news by id '{}'", id);
                default ->
                    log.warn(
                        "Updated more than one subscriptions - {} by id - {}",
                        numberOfTopicsUpdated,
                        id);
              }
            });
  }
}
