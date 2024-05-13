package com.malex.filteringservice.controller;

import com.malex.filteringservice.model.request.FilterRequest;
import com.malex.filteringservice.model.response.FilterResponse;
import com.malex.filteringservice.service.storage.FilterStorageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequestMapping("/v1/filters")
@RequiredArgsConstructor
public class FilterRestController {

    private final FilterStorageService service;

    @GetMapping
    public ResponseEntity<Flux<FilterResponse>> findAll() {
        log.info("HTTP request find all filters");
        return ResponseEntity.ok(service.findAll());
    }

    @PostMapping
    public ResponseEntity<Mono<FilterResponse>> save(@RequestBody FilterRequest request) {
        log.info("HTTP request - {}", request);
        return ResponseEntity.ok(service.save(request));
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> deleteById(@PathVariable String id) {
        log.info("HTTP request, delete filter by id - {}", id);
        return  service.deleteById(id).thenReturn(ResponseEntity.status(HttpStatus.NO_CONTENT).build());
    }
}
