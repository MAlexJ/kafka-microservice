package com.malex.storageservice.controller.handler;

import com.malex.storageservice.model.entity.ItemEntity;
import com.malex.storageservice.repository.ItemRepository;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@RequiredArgsConstructor
public class ItemsRouterHandler {

  private final ItemRepository repository;

  public Mono<ServerResponse> findAll(ServerRequest request) {
    return ServerResponse.ok().body(repository.findAll(), ItemEntity.class);
  }

  public Mono<ServerResponse> create(ServerRequest request) {
    return request
        .bodyToMono(ItemEntity.class)
        .doOnNext(filterRequest -> log.info("HTTP request - {}", filterRequest))
        .flatMap(repository::save)
        .flatMap(
            filterResponse ->
                ServerResponse.created(URI.create("/filters/" + filterResponse.getId()))
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(filterResponse))
        .onErrorResume(
            (e) -> {
              log.error("Error occurred while creating the item", e);
              return ServerResponse.badRequest().build();
            });
  }
}
