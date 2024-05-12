package com.malex.subscriptionservice.scheduler;

import com.malex.subscriptionservice.kafka.producer.KafkaPublisherService;
import com.malex.subscriptionservice.service.SubscriptionService;
import java.time.Duration;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.Disposable;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class SubscriptionProcessingScheduler {

  private final SubscriptionService service;
  private final KafkaPublisherService publisher;

  /**
   * Scheduled and Spring webflux link: <a
   * href="https://stackoverflow.com/questions/54093132/scheduled-and-spring-webflux">@Scheduled and
   * Spring webflux</a>
   */
  @Bean
  public Disposable processingSubscriptions() {
    return Flux.interval(Duration.ofMinutes(1))
        // run every minute
        .publishOn(Schedulers.boundedElastic())
        .onBackpressureDrop()
        // if the task below takes a long time, greater than the next tick, then just drop this tick
        .concatMap(r -> processing(), 0)
        .subscribe();
  }

  private Flux<Void> processing() {
    return Flux.defer(() -> service.findAllActiveSubscriptions().flatMap(publisher::send));
  }
}
