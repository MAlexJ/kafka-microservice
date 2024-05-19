package com.malex.filteringservice.service;

import com.malex.filteringservice.kafka.producer.KafkaProducer;
import com.malex.filteringservice.model.event.RssItem;
import com.malex.filteringservice.service.criteria.FilterCriteriaService;
import com.malex.filteringservice.service.md5.Md5HashService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
@RequiredArgsConstructor
public class LogicProcessor {

  private final Md5HashService md5HashService;
  private final KafkaProducer producerService;
  private final FilterCriteriaService criteriaService;

  public Flux<RssItem> process(Flux<RssItem> items) {
    return items
        .filterWhen(md5HashService::isNotExistItemByMd5Hash)
        .filterWhen(criteriaService::applyFilteringCriteriaIncludedOrExcluded)
        .flatMap(producerService::sendMessage)
        .flatMap(md5HashService::saveItemMd5Hash);
  }
}
