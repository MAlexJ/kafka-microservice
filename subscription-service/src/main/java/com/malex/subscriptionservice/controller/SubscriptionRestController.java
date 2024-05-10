package com.malex.subscriptionservice.controller;

import com.malex.subscriptionservice.model.request.SubscriptionRequest;
import com.malex.subscriptionservice.model.response.SubscriptionResponse;
import com.malex.subscriptionservice.service.SubscriptionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/v1/subscriptions")
@RequiredArgsConstructor
public class SubscriptionRestController {

  private final SubscriptionService service;

  @GetMapping
  public ResponseEntity<List<SubscriptionResponse>> findSubscriptions() {
    log.info("HTTP request find all subscription");
    return ResponseEntity.ok(service.findSubscriptions());
  }

  @PostMapping
  public ResponseEntity<SubscriptionResponse> subscribe(@RequestBody SubscriptionRequest request) {
    log.info("HTTP request - {}", request);
    return ResponseEntity.status(HttpStatus.CREATED).body(service.subscribe(request));
  }

  @PatchMapping("/{id}")
  public ResponseEntity<SubscriptionResponse> unsubscribe(@PathVariable String id) {
    log.info("HTTP request, unsubscribe by id - {}", id);
    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }
}
