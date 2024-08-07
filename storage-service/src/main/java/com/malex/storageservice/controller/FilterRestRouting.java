package com.malex.storageservice.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

import com.malex.storageservice.controller.handler.FilterRouterHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

@Component
@RequiredArgsConstructor
public class FilterRestRouting {

  private final FilterRouterHandler handler;

  @Bean
  public RouterFunction<ServerResponse> routes() {
    return route()
        .path("/v1/filters", builder -> builder.GET(accept(APPLICATION_JSON), handler::findAll))
        .build();
  }
}
