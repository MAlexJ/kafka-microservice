package com.malex.rssreaderservice.model;

import java.time.LocalDateTime;
import java.util.List;
import lombok.Data;

@Data
public class SubscriptionDto {
  private String id;
  private Long chatId;
  private String templateId;
  private String customizationId;
  private String rss;
  private List<String> filterIds;
  private boolean isActive;
  private LocalDateTime created;
}
