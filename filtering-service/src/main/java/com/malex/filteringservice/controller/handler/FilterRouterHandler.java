package com.malex.filteringservice.controller.handler;

import com.malex.filteringservice.model.request.FilterRequest;
import com.malex.filteringservice.model.response.FilterResponse;
import com.malex.filteringservice.service.filter.FilterRestService;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/*
 * 1. Functional Endpoints
 * link:  https://docs.spring.io/spring-framework/reference/web/webflux-functional.html
 *
 * 2. How could I return a different ServerResponse if Flux stream data has error status
 * link:https://stackoverflow.com/questions/58429966/how-could-i-return-a-different-serverresponse-if-flux-stream-data-has-error-stat
 * solution: apply .onError(Exception.class, (e, a) -> ServerResponse.status(HttpStatus.NOT_FOUND).build())
 *           or   .onErrorResume((e) -> ServerResponse.badRequest().build())
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class FilterRouterHandler {

  private final FilterRestService service;

  /*
  * HTTP GET
  * link: https://restfulapi.net/http-methods/
  *
  * 1. Use GET requests to retrieve resource representation/information only – and not modify it in any way.
  *    As GET requests do not change the resource’s state, these are said to be safe methods.
  *
  * 2. Additionally, GET APIs should be idempotent.
  *   Making multiple identical requests must produce the same result every time until another API (POST or PUT)
  *   has changed the state of the resource on the server.
  *
  * GET API Response Codes:
  *
  * 1. For any given HTTP GET API, if the resource is found on the server, then it must return HTTP
  *    response code 200 (OK) – along with the response body, which is usually either XML or JSON content
  *
  * 2. In case the resource is NOT found on the server then API must return HTTP response code 404 (NOT FOUND).
  *
  * 3. Similarly, if it is determined that the GET request itself is not correctly formed then the server
  *    will return the HTTP response code 400 (BAD REQUEST).
  *
  * Example URIs:
  *
      HTTP GET http://www.appdomain.com/users
      HTTP GET http://www.appdomain.com/users?size=20&page=5
      HTTP GET http://www.appdomain.com/users/123
      HTTP GET http://www.appdomain.com/users/123/address
   */
  public Mono<ServerResponse> findAll(ServerRequest request) {
    log.info("HTTP request - find all filters");
    Flux<FilterResponse> filterResponseFlux = service.findAll();
    return ServerResponse.ok()
        .contentType(MediaType.APPLICATION_JSON)
        .body(filterResponseFlux, FilterResponse.class)
        .onErrorResume((e) -> ServerResponse.badRequest().build());
  }

  /*
  * HTTP POST
  * link: https://restfulapi.net/http-methods/
  *
  * 1. Use POST APIs to create new subordinate resources, e.g., a file is subordinate to a directory containing it
  *    or a row is subordinate to a database table
  *
  * 2. Responses to this method are not cacheable unless the response includes appropriate Cache-Control
  *    or Expires header fields.
  *
  * 3. Please note that POST is neither safe nor idempotent, and invoking two identical POST requests will result
  *    in two different resources containing the same information (except resource ids).
  *
  * POST API Response Codes
  *
  * 1. Ideally, if a resource has been created on the origin server, the response SHOULD be
  *    HTTP response code 201 (Created) and contain an entity that describes the status of the request
  *    and refers to the new resource, and a Location header.
  *
  * 2. Many times, action performed by POST method might not result in a resource that can be identified by a URI.
  *    In this case, either HTTP response code 200 (OK) or 204 (No Content) is appropriate response status.
  *
  * Example URIs:
      HTTP POST http://www.appdomain.com/users
      HTTP POST http://www.appdomain.com/users/123/accounts
  */
  public Mono<ServerResponse> create(ServerRequest request) {
    return request
        .bodyToMono(FilterRequest.class)
        .doOnNext(filterRequest -> log.info("HTTP request - {}", filterRequest))
        .flatMap(service::save)
        .flatMap(
            filterResponse ->
                ServerResponse.created(URI.create("/filters/" + filterResponse.id()))
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(filterResponse))
        .onErrorResume((e) -> ServerResponse.badRequest().build());
  }

  /*
  * HTTP DELETE
  * link: https://restfulapi.net/http-methods/#delete
  *
  * 1. DELETE operations are idempotent. If you DELETE a resource, it’s removed from the collection of resources.
  * 2. If the request passes through a cache and the Request-URI identifies one or more currently cached entities,
  *    those entries SHOULD be treated as stale.
  *    Responses to this method are not cacheable.
  *
  * DELETE API Response Codes:
  *
  * 1. A successful response of DELETE requests SHOULD be an HTTP response code 200 (OK) if the response includes
  *    an entity describing the status
  *
  * 2. The status should be 202 (Accepted) if the action has been queued.
  *
  * 3. The status should be 204 (No Content) if the action has been performed but the response
  *    does not include an entity.
  *
  * 4. Repeatedly calling DELETE API on that resource will not change the outcome – however,
  *    calling DELETE on a resource a second time will return a 404 (NOT FOUND) since it was already removed.
  *
  * Example URIs:
       HTTP DELETE http://www.appdomain.com/users/123
       HTTP DELETE http://www.appdomain.com/users/123/accounts/456
  *
  */
  public Mono<ServerResponse> deleteById(ServerRequest request) {
    String id = request.pathVariable("id");
    log.info("HTTP request, delete filter by id - {}", id);

    // todo: how to handle  Mono<Void> ???
    // Mono<Void> voidMono = service.deleteById(id);
    return service
        .deleteById(id)
        // The status should be 204 (No Content) if the action has been performed but the response
        // does not include an entity.
        .then(ServerResponse.noContent().build())
        // Repeatedly calling DELETE API on that resource will not change the outcome – however,
        // calling DELETE on a resource a second time will return a 404 (NOT FOUND) since it was
        // already removed.
        .onErrorResume((e) -> ServerResponse.notFound().build());
  }
}
