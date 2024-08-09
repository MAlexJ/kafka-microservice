package com.malex.filteringservice.model.entity;

import com.malex.filteringservice.model.filter.FilterCondition;
import java.time.LocalDateTime;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Data
@Document(collection = "filters")
@TypeAlias("FilterEntity")
public class FilterEntity {

  @MongoId private String id;

  private FilterCondition condition;

  @CreatedDate private LocalDateTime created;
}
