package com.malex.subscription_service.model.entity;

import java.time.LocalDateTime;
import java.util.List;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Data
@Document(collection = "subscriptions")
@TypeAlias("SubscriptionEntity")
public class SubscriptionEntity {

  @MongoId private String id;
  private Long chatId;
  private String templateId;
  private String customizationId;
  private List<String> filterIds;
  private String rss;
  private boolean isActive;

  @CreatedDate private LocalDateTime created;
}
