package com.malex.storageservice.controller.handler;

import com.malex.storageservice.model.request.ItemRequest;
import com.malex.storageservice.model.response.ItemResponse;
import com.malex.storageservice.service.rest.RestItemService;
import java.net.URI;
import java.util.Objects;
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

  private final RestItemService service;

  public Mono<ServerResponse> findAll(ServerRequest request) {
    //      @RequestParam(value = "page", defaultValue = "1") Integer page,
    //      @RequestParam(value = "size", defaultValue = "10") Long limit,
    //      @RequestParam Map<String, String> filterParams
    // link:
    // https://gatheca-george.medium.com/spring-webflux-using-relational-database-mysql-postgresql-fcc5e487f57f
    return ServerResponse.ok()
        .contentType(MediaType.APPLICATION_JSON)
        .body(service.findAll(), ItemResponse.class)
        .onErrorResume((e) -> ServerResponse.badRequest().build());
  }

  public Mono<ServerResponse> create(ServerRequest request) {
    return request
        .bodyToMono(ItemRequest.class)
        .filter(Objects::nonNull)
        .doOnNext(item -> log.debug("HTTP request - {}", item))
        .flatMap(service::create)
        .flatMap(
            item ->
                ServerResponse.created(URI.create("/filters/" + item.id()))
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(item))
        .onErrorResume(
            (e) -> {
              log.error("Error occurred while creating the item", e);
              return ServerResponse.badRequest().build();
            });
  }
}
