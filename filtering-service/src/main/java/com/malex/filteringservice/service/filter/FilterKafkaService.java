package com.malex.filteringservice.service.filter;

import com.malex.filteringservice.model.entity.FilterEntity;
import com.malex.filteringservice.service.cache.FilterCacheService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FilterKafkaService {

  private final FilterCacheService cacheService;

  public List<FilterEntity> findAll() {
    return cacheService.findAll().toStream().toList();
  }
}
