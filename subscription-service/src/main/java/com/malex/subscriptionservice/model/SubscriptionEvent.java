package com.malex.subscriptionservice.model;

import java.time.LocalDateTime;
import java.util.List;

public record SubscriptionEvent(
    String id,
    Long chatId,
    String templateId,
    String customizationId,
    String rss,
    List<String> filterIds,
    boolean isActive,
    LocalDateTime created) {}
