package com.malex.filteringservice.service.criteria;

import static com.malex.filteringservice.model.filter.ConditionType.EXCLUDE;
import static com.malex.filteringservice.model.filter.ConditionType.INCLUDE;

import com.malex.filteringservice.model.entity.FilterEntity;
import com.malex.filteringservice.model.event.ItemEvent;
import com.malex.filteringservice.model.filter.ConditionType;
import com.malex.filteringservice.model.filter.FilterCondition;
import com.malex.filteringservice.service.filter.FilterKafkaService;
import java.util.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

/** Subscription criteria (include or exclude keywords) filtering service */
@Slf4j
@Service
@RequiredArgsConstructor
public class FilterCriteriaService {

  private static final String TEXT_FORMAT = "%s %s";

  @Value("${filter.criteria.title}")
  private boolean filterCriteriaOnlyToTitle;

  private final FilterKafkaService service;

  /** check whether the filter criteria are included or excluded. */
  public Flux<Boolean> applyFilteringCriteriaIncludedOrExcluded(ItemEvent item) {
    var filterIds = item.filterIds();
    if (Objects.isNull(filterIds) || filterIds.isEmpty()) {
      return Flux.just(true);
    }

    var text = defineBehaviorForTextMessageOrProvideDefault(item);
    var filters =
        service.findAll().stream()
            .filter(filter -> filterIds.contains(filter.getId()))
            .toList();

    return Flux.just(
        hasExcludingFilterMatchingCondition(filters, text)
            && hasInclusiveFilterMatchingCondition(filters, text));
  }

  protected boolean hasExcludingFilterMatchingCondition(List<FilterEntity> filters, String text) {
    return findConditionByType(filters, EXCLUDE).stream()
        .noneMatch(key -> findOccurrencePhraseIgnoreCase(text, key));
  }

  protected boolean hasInclusiveFilterMatchingCondition(List<FilterEntity> filters, String text) {
    List<String> inclusiveWords = findConditionByType(filters, INCLUDE);
    return inclusiveWords.isEmpty()
        || inclusiveWords.stream().anyMatch(key -> findOccurrencePhraseIgnoreCase(text, key));
  }

  /** There is an excluding condition for matching */
  private List<String> findConditionByType(List<FilterEntity> filters, ConditionType type) {
    return filters.stream()
        .map(FilterEntity::getCondition)
        .filter(filter -> type == filter.type())
        .map(FilterCondition::keyWords)
        .flatMap(Collection::stream)
        .toList();
  }

  /** Define the behavior for a text message or provide the default to apply filters */
  private String defineBehaviorForTextMessageOrProvideDefault(ItemEvent item) {
    var title = item.title();
    var description = item.description();
    // default behavior
    if (filterCriteriaOnlyToTitle) {
      return title;
    }
    return String.format(TEXT_FORMAT, title, description);
  }

  /** find the occurrence of specific phrase within a text */
  private boolean findOccurrencePhraseIgnoreCase(String text, String phrase) {
    var textOpt = toLowerCase(text);
    var phraseOpt = toLowerCase(phrase);
    if (textOpt.isEmpty() || phraseOpt.isEmpty()) {
      return false;
    }
    return textOpt.get().contains(phraseOpt.get());
  }

  private Optional<String> toLowerCase(String str) {
    return Optional.ofNullable(str).map(String::toLowerCase);
  }
}
