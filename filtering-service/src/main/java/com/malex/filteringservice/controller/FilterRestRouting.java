package com.malex.filteringservice.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

import com.malex.filteringservice.controller.handler.FilterRouterHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

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
@Component
@RequiredArgsConstructor
public class FilterRestRouting {

  private final FilterRouterHandler handler;

  @Bean
  public RouterFunction<ServerResponse> routes() {
    return route()
        .path(
            "/v1/filters",
            builder ->
                builder
                    .GET(accept(APPLICATION_JSON), handler::findAll)
                    .POST(accept(APPLICATION_JSON), handler::create)
                    .DELETE("{id}", handler::deleteById))
        .build();
  }
}
