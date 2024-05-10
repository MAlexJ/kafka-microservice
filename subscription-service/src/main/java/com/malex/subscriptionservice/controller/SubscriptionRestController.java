package com.malex.subscriptionservice.controller;

import com.malex.subscriptionservice.model.request.SubscriptionRequest;
import com.malex.subscriptionservice.model.response.SubscriptionResponse;
import com.malex.subscriptionservice.service.SubscriptionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * WebFlux supports using a single value reactive type to produce the ResponseEntity asynchronously,
 * and/or single and multi-value reactive types for the body. This allows a variety of async
 * responses with ResponseEntity as follows:
 *
 * <ul>
 *   <li>ResponseEntity<Mono<T>> or ResponseEntity<Flux<T>> make the response status and headers
 *       known immediately while the body is provided asynchronously at a later point. Use Mono if
 *       the body consists of 0..1 values or Flux if it can produce multiple values.
 *   <li>Mono<ResponseEntity<T>> provides all three: response status, headers, and body,
 *       asynchronously at a later point. This allows the response status and headers to vary
 *       depending on the outcome of asynchronous request handling.
 *   <li>Mono<ResponseEntity<Mono<T>>> or Mono<ResponseEntity<Flux<T>>> are yet another possible,
 *       albeit less common alternative. They provide the response status and headers asynchronously
 *       first and then the response body, also asynchronously, second.
 * </ul>
 *
 * Link to info: <a
 * href="https://docs.spring.io/spring-framework/reference/web/webflux/controller/ann-methods/responseentity.html">ResponseEntity</a>
 */
@Slf4j
@RestController
@RequestMapping("/v1/subscriptions")
@RequiredArgsConstructor
public class SubscriptionRestController {

  private final SubscriptionService service;

  @GetMapping
  public ResponseEntity<Flux<SubscriptionResponse>> findSubscriptions() {
    log.info("HTTP: find all subscriptions");
    return ResponseEntity.ok(service.findAllSubscriptions());
  }

  @PostMapping
  public ResponseEntity<Mono<SubscriptionResponse>> subscribe(
      @RequestBody SubscriptionRequest request) {
    log.info("HTTP: subscribe, request - {}", request);
    return ResponseEntity.status(HttpStatus.CREATED).body(service.subscribe(request));
  }

  @PatchMapping("/{id}")
  public Mono<ResponseEntity<Void>> unsubscribe(@PathVariable String id) {
    log.info("HTTP: unsubscribe, id - {}", id);
    return service
        .unsubscribe(id)
        .map(resp -> ResponseEntity.status(HttpStatus.NO_CONTENT).build());
  }
}
